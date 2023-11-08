package com.example.prestigebankingfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowBalance extends AppCompatActivity {

    TextView showbalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_balance);
        showbalance=findViewById(R.id.showbalance);
        String balance=getIntent().getExtras().getString("balance");
        showbalance.setText("Rs."+balance+" is the current balance of this account");
    }
}