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

public class FreeActivity extends AppCompatActivity {

    public String nameFreeData;
    public String imageFreeData;
    public ImageView imageFree;
    public TextView nameFree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        imageFree = findViewById(R.id.imageFree);
        nameFree = findViewById(R.id.nameFree);

        Intent intent = getIntent();
        nameFreeData = intent.getStringExtra("name");
        imageFreeData = intent.getStringExtra("image");

        setFreeData();

    }

    public void payScreen(View view){

        Intent intentScreen = new Intent(this,PayScreenActivity.class);
        startActivity(intentScreen);
    }

    public void setFreeData(){

        nameFree.setText(nameFreeData);
        Glide.with(this).load(imageFreeData).apply(RequestOptions.circleCropTransform()).into(imageFree);
    }
}
