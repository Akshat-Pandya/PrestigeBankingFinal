package com.example.prestigebankingfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView contactus;
    String number,mpin;
    Button debit,credit,loanoptions,bankbalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        contactus=findViewById(R.id.contactUs);
        debit=findViewById(R.id.debitButton);
        credit=findViewById(R.id.creditButton);
        loanoptions=findViewById(R.id.loanButton);
        bankbalance=findViewById(R.id.balanceButton);
        mpin=getIntent().getExtras().getString("mpin");
        number=getIntent().getExtras().getString("phone");

        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debitMoney();
            }
        });
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creditMoney();
            }
        });
        loanoptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoanOptions();
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Help.class);
                startActivity(intent);
            }
        });
        bankbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showBalance();
            }
        });
    }
    private void showBalance() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

// Define the path to the node you want to read
        DatabaseReference nodeReference = databaseReference.child("Prestige Bank Accounts");

// Read the value from the node
        nodeReference.child(number).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot=task.getResult();
                        String balance=String.valueOf(dataSnapshot.child("creditamount").getValue());
                        Intent intent=new Intent(MainActivity.this,ShowBalance.class);
                        intent.putExtra("balance",balance);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showLoanOptions() {
        Intent intent=new Intent(this,LoanPolicies.class);
        startActivity(intent);
    }

    private void creditMoney() {
        Toast.makeText(this, "Feature not yet implemented.", Toast.LENGTH_SHORT).show();
    }


    private void debitMoney() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

// Define the path to the node you want to read
        DatabaseReference nodeReference = databaseReference.child("Prestige Bank Accounts");

// Read the value from the node
        nodeReference.child(number).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot=task.getResult();
                        String balance=String.valueOf(dataSnapshot.child("creditamount").getValue());
                        Intent intent=new Intent(MainActivity.this,DebitMoney.class);
                        intent.putExtra("balance",balance);
                        intent.putExtra("phone",number);
                        intent.putExtra("mpin",mpin);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}