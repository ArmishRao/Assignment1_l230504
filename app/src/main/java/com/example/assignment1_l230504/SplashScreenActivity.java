 package com.example.assignment1_l230504;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class SplashScreenActivity extends AppCompatActivity {
 ImageView  logoAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //creating intent have to ake arrow function because
        //we want to
        new Handler().postDelayed(()->{
                    Intent iOnBoarding=new Intent(SplashScreenActivity.this,onBoardingScreen.class);
                    startActivity(iOnBoarding);
                    finish();
                },5000);
        logoAnim=findViewById(R.id.myImage);
        //now we have to take anim file as variable in this java file
        //java is collection of three things
        //variable ,object,methods

        Animation animLogo= AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        logoAnim.startAnimation(animLogo);
    }
}