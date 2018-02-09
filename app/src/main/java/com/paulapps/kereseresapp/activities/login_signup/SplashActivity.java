package com.paulapps.kereseresapp.activities.login_signup;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.paulapps.kereseresapp.R;

/**
 * @author crist
 */


public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {
    ImageView imgSplash;
    TextView tvSplash;
    Animation fidein;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgSplash = findViewById(R.id.imgSplash);
        tvSplash = findViewById(R.id.tvSplash);

        fidein = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        imgSplash.setAnimation(fidein);
        tvSplash.setAnimation(fidein);

        fidein.setAnimationListener(this);


        //Fuente titulo
        Typeface myFont = Typeface.createFromAsset(getAssets(), "Strawberry Blossom.ttf");
        tvSplash.setTypeface(myFont);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
