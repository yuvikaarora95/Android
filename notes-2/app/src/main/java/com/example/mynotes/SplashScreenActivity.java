package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.mynotes.R;
import com.example.mynotes.helper.SharedPreferenceHelper;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent loggedIn = new Intent(SplashScreenActivity.this,MainActivity.class);
                Intent newUser = new Intent(SplashScreenActivity.this,LoginActivity.class);
                if(SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"username","").equals("")) {
                    SplashScreenActivity.this.startActivity(newUser);
                    SplashScreenActivity.this.finish();
                }
                else {
                    SplashScreenActivity.this.startActivity(loggedIn);
                    SplashScreenActivity.this.finish();
                }
            }
        }, 2000);
    }
    }

