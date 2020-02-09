package com.furkanzayimoglu.mobilliumdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.furkanzayimoglu.mobilliumdemo.R;

import java.util.Objects;

public class PayScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_screen);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

    }
}
