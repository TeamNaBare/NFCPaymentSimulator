package com.cvika.mobv.nfcpaymentsimulator;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Nacitanie informacii o karte cez NFC do SharedPreferences
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}