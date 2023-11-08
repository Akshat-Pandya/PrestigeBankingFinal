package com.example.prestigebankingfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email,password;
    ProgressBar progressBar;
    Button login;
    TextView createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        login=findViewById(R.id.signin);
        createAccount=findViewById(R.id.createact);
        progressBar.setVisibility(View.INVISIBLE);
        auth=FirebaseAuth.getInstance();
        checkUserSignedIn();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });
    }

    private void checkUserSignedIn() {
        if(auth.getCurrentUser()!=null && auth.getCurrentUser().isEmailVerified())
        {
            Intent intent=new Intent(Login.this,Welcome.class);
            startActivity(intent);
            finish();
        }

    }

    private void signInUser() {
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

            auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                            Toast.makeText(Login.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                               Intent intent=new Intent(Login.this,Welcome.class);
                               startActivity(intent);
                            finish();}
                            else{
                                showVerificationDialog();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this, "Failed to signin .", Toast.LENGTH_SHORT).show();
                        }
                    });


            progressBar.setVisibility(View.VISIBLE);

    }
    private void showVerificationDialog() {
        AlertDialog.Builder obj=new AlertDialog.Builder(this); // do not use getApplicationContext() here
        obj.setTitle("Delete");
        obj.setMessage("Email verification");
        obj.setCancelable(false);
        obj.setIcon(R.drawable.baseline_mark_email_read_24); // Vector asset
        obj.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Gmail app or an email app is not available on the device. Handle this case as needed.
                }
            }
        });
        obj.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Login.this, "Email Verification required", Toast.LENGTH_SHORT).show();
            }
        });
        obj.show();
    }
}