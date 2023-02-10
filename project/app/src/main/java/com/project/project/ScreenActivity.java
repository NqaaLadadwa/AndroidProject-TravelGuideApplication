package com.project.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


public class ScreenActivity extends AppCompatActivity {

    Handler handler;
    ImageView img;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        img = findViewById(R.id.img);
        img.animate().alpha(4000).setDuration(1000);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(ScreenActivity.this,MainActivity.class);
                startActivity(dsp);
                finish();
            }
        },4000);
    }
}