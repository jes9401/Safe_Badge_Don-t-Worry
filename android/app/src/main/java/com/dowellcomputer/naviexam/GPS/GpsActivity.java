package com.dowellcomputer.naviexam.GPS;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.dowellcomputer.naviexam.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kyleduo.switchbutton.SwitchButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GpsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private AlertDialog dialog;
    private String GPS, personal_id,name;
    private String gps1, gps2;
    private Double gps11, gps22;
    public int state=0;
    private ImageButton back_btn,reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        SwitchButton switchbtn = (SwitchButton)findViewById(R.id.switchbtn);
        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).hide();

        Intent intent = getIntent();
        personal_id = intent.getStringExtra("personal_id");
        Log.d(this.getClass().getName(),"실행:고유번호 받기"+personal_id);
        name = intent.getStringExtra("name");
        Log.d(this.getClass().getName(),"실행:이름 받기"+name);
        GPS = intent.getStringExtra("GPS");
        gps1 = GPS.split(",")[0];
        gps2 = GPS.split(",")[1];
        gps11 = Double.parseDouble(gps1);
        gps22 = Double.parseDouble(gps2);

        back_btn = (ImageButton)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        reload = (ImageButton)findViewById(R.id.reload);

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GpsActivity.BackgroundTask().execute();
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        gps1 = GPS.split(",")[0];
                        gps2 = GPS.split(",")[1];
                        gps11 = Double.parseDouble(gps1);
                        gps22 = Double.parseDouble(gps2);
                        FragmentManager fragmentManager = getFragmentManager();
                        MapFragment mapFragment = (MapFragment) fragmentManager
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(GpsActivity.this);
                        fragmentManager.beginTransaction().show(mapFragment).commit();
                        AlertDialog.Builder builder = new AlertDialog.Builder(GpsActivity.this);
                        dialog = builder.setMessage("위치를 갱신했습니다.")
                                .setPositiveButton("닫기", null)
                                .create();
                        dialog.show();

                    }},2000);

            }
        });


        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(GpsActivity.this);
        fragmentManager.beginTransaction().hide(mapFragment).commit();  //hide옵션으로 기본 상태에서 fragment를 숨겨놓음

        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    reload.setVisibility(View.VISIBLE);

                    FragmentManager fragmentManager = getFragmentManager();
                    MapFragment mapFragment = (MapFragment)fragmentManager
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(GpsActivity.this);
                    fragmentManager.beginTransaction().show(mapFragment).commit();
                    state=1;
                    if(state==1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(GpsActivity.this);
                        dialog = builder.setMessage("GPS를 켰습니다.")
                                .setPositiveButton("닫기", null)
                                .create();
                        dialog.show();
                    }
                }
                else{
                    FragmentManager fragmentManager = getFragmentManager();
                    MapFragment mapFragment = (MapFragment)fragmentManager
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(GpsActivity.this);
                    fragmentManager.beginTransaction().hide(mapFragment).commit();  //hide 옵션으로 숨김
                    state=0;
                }

            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng lat1 = new LatLng(gps11,gps22);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(lat1);
        markerOptions.title(name);
        markerOptions.snippet("현재 위치");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(lat1));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        @Override
        protected void onPreExecute() {
            target = "http://jes9401.pythonanywhere.com/web/getInfo/"+personal_id;


        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); // 넘어오는 결과값들을 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // 해당 inputstream에 있는 내용들을 버퍼에 담아 읽을수 있도록 함.
                String temp;
                StringBuilder stringBuilder = new StringBuilder(); // 문자열 형태로 저장
                while ((temp = bufferedReader.readLine()) != null) {  // 버퍼에서 받아오는 값을 한줄씩 읽으면 temp에 저장
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) { // 해당 결과 처리
            try {
                JSONObject jsonResponse = new JSONObject(result);
                GPS = jsonResponse.getString("gps"); //gps 값
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}