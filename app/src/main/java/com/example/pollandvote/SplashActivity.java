package com.example.pollandvote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView t;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        t=findViewById(R.id.textviewsplash);
        t.animate().translationX(1000).setDuration(1000).setStartDelay(2500);

        Thread thread= new Thread(() -> {
            try{
                Thread.sleep(4000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {

                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        thread.start();
    }
}