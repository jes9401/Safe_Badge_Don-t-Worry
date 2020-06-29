package com.dowellcomputer.naviexam.Record;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dowellcomputer.naviexam.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordActivity extends AppCompatActivity {

    private String cctvURL, GPS, post_title, post_text, public_code2,personal_code2, post2_text, post2_title;
    public String public_id="";
    public String personal_id=null;

    ImageButton record_stop,record_start,back_btn;
    MediaRecorder mediaRecorder; //녹음을 도와주는 객체
    MediaPlayer mediaPlayer; //재생을 위한 객체
    EditText edit;
    String path,mFileName=null;
    boolean isRecording = false; //녹음중인지 아닌지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).hide();
//기기마다 경로가 다르기 때문에 외부저장소 경로를 가져온다 / 없으면 내부저장소 경로를 가져옴
        path = "/sdcard/Music/"; //녹음저장확장자 3gp
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyMMddHHmmss");

        mFileName =timeStampFormat.format(new Date()).toString();

        back_btn= findViewById(R.id.back_btn);
        edit=findViewById(R.id.edit);
        record_start = findViewById(R.id.record_start);
        record_stop = findViewById(R.id.record_stop);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//권한승인여부 확인후 메시지 띄워줌(둘 중 하나라도)


//마이크가 있는지 없는지 확인
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) { //현재 하드웨어 정보를 가져옴
            record_stop.setEnabled(false);
            record_start.setEnabled(true);
        }else{
            record_stop.setEnabled(false);
            record_start.setEnabled(false);
        }

        record_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_start.setVisibility(View.GONE); //두번 누를 수 있으니까
                record_stop.setVisibility(View.VISIBLE);
                record_stop.setEnabled(true);

                isRecording = true;

                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //마이크로 녹음하겠다
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR); //저장파일 형식 녹음파일은 3gp로 저장
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); //인코딩 방식설정
                mediaRecorder.setOutputFile(path+mFileName); //경로설정

                try {
                    mediaRecorder.prepare(); //녹음을 준비함 : 지금까지의 옵션에서 문제가 발생했는지 검사함
                    mediaRecorder.start();
                    Toast.makeText(getApplicationContext(), "녹음시작", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        record_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_start.setEnabled(true);
                record_start.setVisibility(View.VISIBLE);
                record_stop.setVisibility(View.GONE);

                if(isRecording){
                    mediaRecorder.stop();
                    mediaRecorder = null;
                    isRecording=false;
                    Toast.makeText(getApplicationContext(), "녹음중지", Toast.LENGTH_SHORT).show();

                }else{
                    mediaPlayer.stop();
                    mediaPlayer=null;
                    Toast.makeText(getApplicationContext(), "재생중지", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1==1){
                record_start.setEnabled(true);
                record_stop.setEnabled(false);
            }
        }
    };

    public class myThread implements Runnable{

        @Override
        public void run() {
            while(true){
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer=null;

                    Message msg = new Message();
                    msg.arg1=1;
                    handler.sendMessage(msg);
                    return;
                }
            }
        }
    }

}