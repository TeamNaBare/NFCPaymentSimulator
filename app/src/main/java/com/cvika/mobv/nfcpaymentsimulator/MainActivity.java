package com.cvika.mobv.nfcpaymentsimulator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    public static final String CARD_ID_KEY = "com.cvika.mobv.nfcpaymentsimulator.shared.CARD_ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveCardId("CARD123456");

        //TODO: Nacitanie informacii o karte cez NFC do SharedPreferences
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    public void saveCardId(String cardId){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CARD_ID_KEY, cardId);
        editor.apply();
    }
}