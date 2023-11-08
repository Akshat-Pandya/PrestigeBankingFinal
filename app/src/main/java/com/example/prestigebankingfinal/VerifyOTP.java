package com.example.prestigebankingfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VerifyOTP extends AppCompatActivity {

    Button verify;
    EditText enteredotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        getSupportActionBar().hide();
        verify=findViewById(R.id.verify);
        enteredotp=findViewById(R.id.enteredotp);
        String number,mpin;
        mpin=getIntent().getExtras().getString("mpin");
        number=getIntent().getExtras().getString("phone");
        int checkotp=getIntent().getExtras().getInt("otp");
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.toString(checkotp).equals(enteredotp.getText().toString()))
                {
                    Toast.makeText(VerifyOTP.this, "Correct OTP", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(VerifyOTP.this,MainActivity.class);
                    intent.putExtra("mpin",mpin);
                    intent.putExtra("phone",number);
                    startActivity(intent);
                    finish();
                }
                else{
                    enteredotp.requestFocus();
                    enteredotp.setError("Incorrect otp");
                    Toast.makeText(VerifyOTP.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}