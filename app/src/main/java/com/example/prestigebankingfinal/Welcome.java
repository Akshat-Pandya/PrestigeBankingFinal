package com.example.prestigebankingfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {

    private Button getStarted;
    private List<Model> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        getStarted=findViewById(R.id.getStarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this, AccountManager.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        populateList();
        RecyclerAdapterW adapter=new RecyclerAdapterW(this,dataList);
        recyclerView.setAdapter(adapter);


    }

    private void populateList() {
        dataList=new ArrayList<>();
        dataList.add(new Model("Visa Card","Debit Money with ease anywhere anytime . Also avail exclusive benefits offered time to time in form of gift vouchers , coupons and manymore.",R.mipmap.visacard));
        dataList.add(new Model("Shopping Card","Avail the exclusive benefits of  Generous Reward Points , Zero Annual Fee & Joining Fee ,Travel Benefits and Contactless Payments.",R.mipmap.shoppingcard_));
        dataList.add(new Model("Master Card","Mastercard combines comprehensive travel & lifestyle benefits with constant support . POM for cardholders whether at home, travelling abroad or making everyday purchases.",R.mipmap.mastervisa));
        dataList.add(new Model("Platinum Master Card","Access benefits like Mastercard lowest hotel rate guarantee,Mastercard luxury hotel and resorts portfolio,Airport Concierge,DoorDash discounts and what not. ",R.mipmap.platinummastercard));
        dataList.add(new Model("Credit Card","Credit Card is one of the most convenient methods of making payments.Get Interest-free credit,Rewards,CashBacks and discounts,Expense tracker and Credit score",R.mipmap.creditcard));

    }

}