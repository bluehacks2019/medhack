package com.example.med_hack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    Handler h;

    SharedPreferences sharedPreferences;

    String mykey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mykey = sharedPreferences.getString("mykey","");


        h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mykey.equals("")){
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        }, 3000);

    }
}
