package com.dowellcomputer.naviexam.ETC;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dowellcomputer.naviexam.R;

public class Loadingclass extends Activity {
    private ImageView imgJolrin;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingclass);
        initView();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);   //3초의 지연시간
    }
    private void initView(){
        imgJolrin=(ImageView)findViewById(R.id.img_android);
        anim= AnimationUtils.loadAnimation(this, R.anim.loading);
        imgJolrin.setAnimation(anim);
    }


}