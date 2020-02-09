package com.furkanzayimoglu.mobilliumdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.furkanzayimoglu.mobilliumdemo.R;

import java.util.Objects;

public class PremiumActivity extends AppCompatActivity {

    public ImageView imagePremium;
    public TextView namePremium;
    public TextView statusPremium;
    public String nameData;
    public String imageData;
    public String statusData;
    public String upperStatusData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        imagePremium = findViewById(R.id.imagePremium);
        namePremium = findViewById(R.id.namePremium);
        statusPremium = findViewById(R.id.statusPremium);

        Intent intent = getIntent();
        nameData = intent.getStringExtra("name");
        imageData = intent.getStringExtra("image");
        statusData = intent.getStringExtra("status");
        upperStatusData = statusData.substring(0, 1).toUpperCase() + statusData.substring(1);
        // gelen veride "premium" baş harfi küçüktü o nedenle sadece baş harfini büyülttüm.

        setData();

    }

    public void setData(){

        namePremium.setText(nameData);
        statusPremium.setText(upperStatusData);
        Glide.with(this).load(imageData).apply(RequestOptions.circleCropTransform()).into(imagePremium);  // bitmap te kullanılabilir.
    }

    public void dateScreen(View view){

        Intent intent = new Intent(this, DateScreenActivity.class);
        startActivity(intent);
    }
}
