package com.example.prestigebankingfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class UseAccount extends AppCompatActivity {

    Button proceed;
    EditText editTextmpin,editTextphone;
    String email,mpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_account);
        proceed=findViewById(R.id.proceed);
        editTextmpin=findViewById(R.id.editTextMPIN);
        editTextphone=findViewById(R.id.editTextPhoneNumber);

        ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

// Define the path to the node you want to read
                DatabaseReference nodeReference = databaseReference.child("Prestige Bank Accounts");

// Read the value from the node
                nodeReference.child(editTextphone.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult().exists())
                            {

                                DataSnapshot dataSnapshot=task.getResult();
                                mpin=String.valueOf(dataSnapshot.child("mpin").getValue());
                                if(mpin.equals(editTextmpin.getText().toString())){
                                    email=String.valueOf(dataSnapshot.child("email").getValue());

                                    progressBar.setVisibility(View.VISIBLE);
                                    int otp=0;
                                    while(otp==0)
                                        otp=getRandomNumber();
                                    String to = email; //vreplace with required mail id
                                    String subject = "Account Verification";
                                    String message = otp +" is the otp for your bank account verification . Do not share this otp with anyone .\n\n\n\nRegards,]nPrestige Bank";

                                    EmailSender emailSender = new EmailSender(to, subject, message);
                                    emailSender.execute();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent intent=new Intent(UseAccount.this,VerifyOTP.class);
                                    intent.putExtra("otp",otp);
                                    intent.putExtra("mpin",editTextmpin.getText().toString());
                                    intent.putExtra("phone",editTextphone.getText().toString());
                                    startActivity(intent);
                                    finish();}
                                else{
                                    Toast.makeText(UseAccount.this, "Invalid MPIN", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(UseAccount.this, "Network error", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(UseAccount.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    int getRandomNumber()
    {
        Random random = new Random();
        int min = 1000; // Minimum 4-digit number
        int max = 9999; // Maximum 4-digit number

        int random4DigitNumber = random.nextInt(max - min + 1) + min;
        return random4DigitNumber;
    }
}