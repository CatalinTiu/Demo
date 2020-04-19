package com.example.parkingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Chitanta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitanta);

        Intent intent = getIntent();
        final String nume = intent.getStringExtra(DetaliiLoc.EXTRA_MESSAGE1);
        final String loc_parcare = intent.getStringExtra(DetaliiLoc.EXTRA_MESSAGE2);
        final String nr_ore = intent.getStringExtra(DetaliiLoc.EXTRA_MESSAGE3);

    }
}
