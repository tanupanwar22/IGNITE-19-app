package com.NITK.ignite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class splash extends AppCompatActivity {
private static int splashtimeout=3000;

String s1,s2,s3,s4;
    private ImageView loader1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        loader1=findViewById(R.id.gif2);
        loader1.setVisibility(View.INVISIBLE);
        Intent intent1=getIntent();
        s1=intent1.getStringExtra("UUID");
        s2=intent1.getStringExtra("username");
        s3=intent1.getStringExtra("mFlag");



        loader1 = findViewById(R.id.gif2);

        loader1.setVisibility(View.INVISIBLE);
        loader1.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splash.this,MainActivity.class);
                intent.putExtra("UUID",s1);
                intent.putExtra("username",s2);
                intent.putExtra("mFlag",s3);
                startActivity(intent);
                finish();

            }
        },splashtimeout);
    }
}
