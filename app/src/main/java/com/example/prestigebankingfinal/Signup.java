package com.example.prestigebankingfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    private Button register;
    private ProgressBar progressBar;
    private EditText email,pwd,confirmpwd;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        progressBar=findViewById(R.id.progressBar);
        email=findViewById(R.id.email_);
        pwd=findViewById(R.id.password_);
        progressBar.setVisibility(View.INVISIBLE);
        confirmpwd=findViewById(R.id.confirmpassword);
        register=findViewById(R.id.register);
        progressBar.setVisibility(View.INVISIBLE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                registerUser();
            }
        });
    }

    private void registerUser() {
        mAuth=FirebaseAuth.getInstance();

        if(email.getText().toString()==null || pwd.getText().toString()==null || confirmpwd.getText().toString()==null)
        {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Text fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pwd.getText().toString().equals(confirmpwd.getText().toString()))
        {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Passwords in text fields do not match kindly reenter", Toast.LENGTH_SHORT).show();
            pwd.requestFocus();
            return;
        }
        String emailstr,pwdstr;
        emailstr=email.getText().toString();
        pwdstr=pwd.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailstr,pwdstr)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Signup.this, "User signup successful.", Toast.LENGTH_SHORT).show();
                        mAuth.getCurrentUser().sendEmailVerification();
                        Intent intent=new Intent(Signup.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, "User signup unsuccessful.", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR",e.toString());
                    }
                });
    }
}