package com.thesaugat.ecommerce.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.home.MainActivity;
import com.thesaugat.ecommerce.userAccount.UserAccountActivity;
import com.thesaugat.ecommerce.utils.SharedPrefUtils;

public class SplashScreenActivity extends AppCompatActivity {
    boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLogged = SharedPrefUtils.getBool(SplashScreenActivity.this, getString(R.string.isLogged), false);
                System.out.println(isLogged);
                if (isLogged)
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                else
                    startActivity(new Intent(SplashScreenActivity.this, UserAccountActivity.class));
                finish();
            }
        }, 400);


    }


}