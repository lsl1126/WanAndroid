package com.lsl.wanandroid.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lsl.wanandroid.R;
import com.lsl.wanandroid.ui.main.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lsl on 2020/6/15/015.
 */
public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(image, "scaleX", 3f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(image, "scaleY", 3f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(2000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }, 500);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
