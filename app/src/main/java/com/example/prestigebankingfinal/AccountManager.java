package com.example.prestigebankingfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountManager extends AppCompatActivity {

    private Button createAccount;
    private Button useAccount;
    private TextView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        getSupportActionBar().hide();
        createAccount=findViewById(R.id.createAccount);
        useAccount=findViewById(R.id.useAccount);
        help=findViewById(R.id.help);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountManager.this,CreateAccount.class);
                startActivity(intent);
            }
        });
        useAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountManager.this,UseAccount.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountManager.this,Help.class);
                startActivity(intent);
            }
        });
    }
}