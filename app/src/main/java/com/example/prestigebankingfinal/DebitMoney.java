package com.example.prestigebankingfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DebitMoney extends AppCompatActivity {

    Button debitButton;
    EditText debitAmount;

    String balance,number,mpin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_money);
        debitButton=findViewById(R.id.debitButton);
        debitAmount=findViewById(R.id.edittextdebit);
        balance=getIntent().getExtras().getString("balance");
        number=getIntent().getExtras().getString("phone");
        mpin=getIntent().getExtras().getString("mpin");
        int updatedbalance=Integer.parseInt(balance);

        debitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debitMoney();
            }
        });



    }

    private void debitMoney() {
        if(debitAmount.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Integer.parseInt(debitAmount.getText().toString())>Integer.parseInt(balance))
        {
            Toast.makeText(this, "Insufficient bank balance", Toast.LENGTH_SHORT).show();
        }
        else{



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
                            String name=String.valueOf(dataSnapshot.child("name").getValue());

                            String address=String.valueOf(dataSnapshot.child("address").getValue());

                            String phonenumber=String.valueOf(dataSnapshot.child("phoneenumber").getValue());

                            String dob=String.valueOf(dataSnapshot.child("dob").getValue());
                            String pin=String.valueOf(dataSnapshot.child("pin").getValue());

                            String email=String.valueOf(dataSnapshot.child("email").getValue());

                            String gender=String.valueOf(dataSnapshot.child("gender").getValue());

                            String image=String.valueOf(dataSnapshot.child("image").getValue());

                            String creditamount=String.valueOf(dataSnapshot.child("creditamount").getValue());
                            int updatedbalance=Integer.parseInt(creditamount)-Integer.parseInt(debitAmount.getText().toString());
                            String ubalance=Integer.toString(updatedbalance);



                            DataModel dataModel=new DataModel(name,number,address,email,pin,image,dob,gender,ubalance,mpin);
                            nodeReference.child(number).setValue(dataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DebitMoney.this, "Amount debited successfully", Toast.LENGTH_SHORT).show();

                                    Toast.makeText(DebitMoney.this, "Updated Balance:\t"+ubalance, Toast.LENGTH_LONG).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DebitMoney.this, "Failed to debit amount", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else{
                            Toast.makeText(DebitMoney.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(DebitMoney.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}