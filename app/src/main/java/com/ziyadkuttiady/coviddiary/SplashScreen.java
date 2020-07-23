package com.ziyadkuttiady.coviddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int SPLASH_SCREEN_TIMEOUT = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                String name = sharedPreferences.getString("name_of_user", "no");
                String phone = sharedPreferences.getString("phone_number_of_user", "no");
                String address = sharedPreferences.getString("address_of_user", "no");

                if (name.equals("no") &&
                        phone.equals("no") &&
                        address.equals("no") ) {
                    startActivity(new Intent(SplashScreen.this, PersonalDataCollectActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, HomeScreenActivity.class));
                    finish();
                }

            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}
