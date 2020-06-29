package com.dowellcomputer.naviexam.SOS;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.dowellcomputer.naviexam.CCTV.CctvActivity;
import com.dowellcomputer.naviexam.ETC.MainActivity;
import com.dowellcomputer.naviexam.GPS.GpsActivity;
import com.dowellcomputer.naviexam.R;
import com.google.android.gms.maps.MapFragment;

public class SosActivity extends AppCompatActivity{
    private ImageButton back_btn,cctv_btn,gps_btn,call_btn;
    private String cctvURL;
    private String GPS;
    private String USER;
    String post_title;
    String post_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        Intent intent = getIntent();
        cctvURL = intent.getStringExtra("cctvURL"); // 로그인한 사용자의 닉네임을 받아옴
        GPS = intent.getStringExtra("GPS");
        Log.d(this.getClass().getName(),"sos액티비티 cctv 값 받아오기");
        Log.d(this.getClass().getName(),cctvURL);
        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).hide();
        call_btn =(ImageButton) findViewById(R.id.call_btn);
        gps_btn=(ImageButton)findViewById(R.id.gps_btn);
        cctv_btn=(ImageButton)findViewById(R.id.cctv_btn);
        back_btn=(ImageButton) findViewById(R.id.back_btn);




        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                startActivity(intent);
            }
        });
        cctv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SosActivity.this, CctvActivity.class);
                intent.putExtra("cctvURL", cctvURL);
                startActivity(intent);
                Log.d(this.getClass().getName(),"인텐트 시작");
            }
        });
        gps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SosActivity.this, GpsActivity.class);
                intent.putExtra("GPS", GPS);
                startActivity(intent);
                Log.d(this.getClass().getName(),"인텐트 시작");
            }
        });
    }

}
