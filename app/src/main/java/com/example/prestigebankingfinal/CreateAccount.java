package com.example.prestigebankingfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CreateAccount extends AppCompatActivity {
    EditText name, email, phonenumber, dateofbirth, password, confirm_password, resd_address,creditamount;
    RadioGroup gender;

    Button browse;
    Button register;
    ImageView addphoto;
    RadioButton checkedgender;
    String imageStorageName;
    String imageCloudUrl;
    ProgressBar progressBar;
    Uri imageUri;
    int radioCheckedId;
    private final int GALLERY_REQ_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        name = findViewById(R.id.name_edittext);
        email = findViewById(R.id.email_edittext);
        creditamount=findViewById(R.id.creditamount_edittext);
        progressBar = findViewById(R.id.progressbar);
        phonenumber = findViewById(R.id.mobno_edittext);
        password = findViewById(R.id.pwd_edittext);
        browse = findViewById(R.id.browse);
        addphoto = findViewById(R.id.addphoto);
        gender = findViewById(R.id.radiogroup_gender);
        confirm_password = findViewById(R.id.confirmpwd_edittext);
        resd_address = findViewById(R.id.address_edittext);
        dateofbirth = findViewById(R.id.dateofbirth_edittext);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkParameterValidity();
            }
        });
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // This will allow the user to select the gallery app of user's choice -> basically we are creating an intent chooser here
                // This is called implicit intent passing means shifting the intent from one app to another app (of a particular category ) installed in same device.
                // Agar aap dusri app me jaakar waha se return me kuch data le kar aa rahe hai toh hamesha startActivityforresult use karna because data fetch hote hi pehla number onActivityResult(---) ye method call hota hai
                startActivityForResult(intent, GALLERY_REQ_CODE);
                //Note : setData() is used to point to the location of a data object which is already present via uri (here image present in external storage (gallery))  (like a file for example), while putExtra() adds simple data types (such as an SMS text string for example).
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                imageUri = data.getData();
                addphoto.setImageURI(imageUri);//A Uniform Resource Identifier (URI) is a unique sequence of characters that identifies a logical or physical resource (here image)
            }
        } else {
            Toast.makeText(this, "Something went wrong ! Please try again :)", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadToFirebaseSTR() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        Date date = new Date();
        imageStorageName = formatter.format(date).toString();
        progressBar.setVisibility(View.VISIBLE);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("/UserImages");
        storageReference.child(imageStorageName).putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();

                        while (!task.isComplete()) ;
                        Uri urlimage = task.getResult();
                        imageCloudUrl = urlimage.toString();

                        uploadToFirebaseRTD();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.e("ERROR UPLOADING", "Failed to create account");
                        Toast.makeText(CreateAccount.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void uploadToFirebaseRTD() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Prestige Bank Accounts");
        if (imageCloudUrl != null) {
            Log.d("URIIMAGE", imageCloudUrl);
        }
        String mpin=generateMPIN();
        DataModel obj = new DataModel(name.getText().toString(), phonenumber.getText().toString().trim(), resd_address.getText().toString(), email.getText().toString(), password.getText().toString(), imageCloudUrl, dateofbirth.getText().toString(), checkedgender.getText().toString(),creditamount.getText().toString(),mpin);
        databaseReference.child(phonenumber.getText().toString()).setValue(obj)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccount.this, "Bank Account Created Successfully", Toast.LENGTH_SHORT).show();
                            Log.d("SUCCESS", "Bank Account Created Successfully");
                            String to = email.getText().toString(); //vreplace with required mail id
                            String subject = "Account MPIN";
                            String message = mpin +" is the mpin for your bank account. Do not share this mpin with anyone this is a higly critical credential.\n\n\n\nRegards,\nPrestige Bank";

                            EmailSender emailSender = new EmailSender(to, subject, message);
                            emailSender.execute();

                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(CreateAccount.this, "Network error", Toast.LENGTH_SHORT).show();
                            Log.e("FAILED", "Failed to create bank account");
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private String generateMPIN() {
        Random random = new Random();

        // Generate a random 7-digit integer
        int min = 1000000; // Smallest 7-digit integer (1,000,000)
        int max = 9999999; // Largest 7-digit integer (9,999,999)

        int randomSevenDigitInt = random.nextInt((max - min) + 1) + min;
        return Integer.toString(randomSevenDigitInt);
    }

    private void checkParameterValidity() {

        if (name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return;
        }
        if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }
        if (phonenumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            phonenumber.requestFocus();
            return;
        }
        if (dateofbirth.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            dateofbirth.requestFocus();
        }
        if (password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }
        if (confirm_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            confirm_password.requestFocus();
            return;
        }
        if (creditamount.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            creditamount.requestFocus();
            return;
        }

        if (resd_address.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            resd_address.requestFocus();
            return;
        }
        if (!password.getText().toString().equals(confirm_password.getText().toString())) {
            Toast.makeText(this, "Password in both fields should be same", Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }
        if (!checkEmailValidity(email.getText().toString())) {
            Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageUri.toString().isEmpty()) {
            Toast.makeText(this, "Please upload a passport size photo", Toast.LENGTH_SHORT).show();
            return;
        }
        radioCheckedId = gender.getCheckedRadioButtonId();
        if (radioCheckedId == -1) {
            Toast.makeText(this, "Pleasse select gender", Toast.LENGTH_SHORT).show();
            return;
        } else {
            checkedgender = findViewById(radioCheckedId);
        }

        uploadToFirebaseSTR();

    }

    private boolean checkEmailValidity(String emailaddress) {
        emailaddress = emailaddress.trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailaddress.matches(emailPattern)) {
            return true;
        } else {
            return false;
        }
    }
}
