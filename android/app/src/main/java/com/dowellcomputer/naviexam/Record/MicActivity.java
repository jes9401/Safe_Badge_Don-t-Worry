package com.dowellcomputer.naviexam.Record;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.dowellcomputer.naviexam.CCTV.CctvActivity;
import com.dowellcomputer.naviexam.ETC.MainActivity;
import com.dowellcomputer.naviexam.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MicActivity extends Activity {
    private String cctvURL, GPS, post_title, post_text, public_code2,personal_code2, post2_text, post2_title;
    public String public_id="";
    public String personal_id=null;
    private static final String TAG = "MAIN";
    private RequestQueue queue;
    private ImageButton back_btn;
    private ImageButton btnPlay;
    private ImageButton btnStop;
    SwipeRefreshLayout swipeRefreshLayout;
    private Button listmic_btn,record_btn,listclose,listdelete,mp3btn;
    private ListView listViewMP3;
    TextView tvMp3,tvTime;
    ArrayList<String> mp3List;
    String selectedMP3;
    String mp3Path = "/sdcard/Voice Recorder/";
    //String mp3Path = "/sdcard/Music/";
    MediaPlayer mPlayer;
    SeekBar pbMP3; // 음악 재생위치를 나타내는 시크바
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic);
        swipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.Swipe);
        final String[] fileName = new String[1];
        //final String[] extName = new String[1];
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);
        mp3List = new ArrayList<String>();
        File[] listFiles = new File(mp3Path).listFiles();
        for(File file : listFiles){
            fileName[0] = file.getName();
            //extName[0] = fileName[0].substring(fileName[0].length()-3);
            //if(extName[0].equals((String) "3gp"))
                mp3List.add(fileName[0]);
        }
        mp3btn=(Button)findViewById(R.id.mp3btn);
        back_btn=(ImageButton) findViewById(R.id.back_btn);
        listclose=(Button)findViewById(R.id.listclose);
        record_btn=(Button)findViewById(R.id.record_btn);
        listmic_btn=(Button)findViewById(R.id.listmic_btn);
        btnPlay =(ImageButton) findViewById(R.id.btnPlay);
        btnStop =(ImageButton) findViewById(R.id.btnStop);
        tvMp3=(TextView)findViewById(R.id.tvMp3);
        tvTime=(TextView)findViewById(R.id.tvTime);
        pbMP3=(SeekBar) findViewById(R.id.pbMP3);



        listmic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listclose.setVisibility(View.VISIBLE);
                listViewMP3.setVisibility(View.VISIBLE);
            }
        });
        listclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewMP3.setVisibility(View.INVISIBLE);
                listclose.setVisibility(View.INVISIBLE);
            }
        });

        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName compName = new ComponentName("com.sec.android.app.voicenote","com.sec.android.app.voicenote.main.VNMainActivity");
                Intent intent23 = new Intent(Intent.ACTION_MAIN);
                intent23.addCategory(Intent.CATEGORY_LAUNCHER);
                intent23.setComponent(compName);
                startActivity(intent23);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listViewMP3 = (ListView)findViewById(R.id.listViewMp3);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,mp3List);
        listViewMP3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewMP3.setAdapter(adapter);
        listViewMP3.setItemChecked(0,true);

        listViewMP3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectedMP3 = mp3List.get(arg2);
            }
        });
        /*listdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = listViewMP3.getCheckedItemPosition();
                if(id !=ListView.INVALID_POSITION){
                    mp3List.remove(id);
                    listViewMP3.clearChoices();
                    adapter.notifyDataSetChanged();
                }
            }
        });*/
        selectedMP3 = mp3List.get(0);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mPlayer = new MediaPlayer();
                    mPlayer.setDataSource(mp3Path + selectedMP3);
                    mPlayer.prepare();
                    mPlayer.start();
                    mPlayer.setLooping(false);
                    btnPlay.setClickable(false);
                    btnStop.setClickable(true);
                    tvMp3.setText(selectedMP3);
                    new Thread(){
                        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                        public void run(){
                            if(mPlayer == null)return;
                            pbMP3.setMax(mPlayer.getDuration());
                            while (mPlayer.isPlaying()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pbMP3.setProgress(mPlayer.getCurrentPosition());
                                        tvTime.setText(timeFormat.format(mPlayer.getCurrentPosition()));
                                    }
                                });
                                SystemClock.sleep(200);
                            }
                        }
                    }.start();
                }catch (IOException e){
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                mPlayer.reset();
                btnPlay.setClickable(true);
                btnStop.setClickable(false);
                pbMP3.setProgress(0);
                tvTime.setText("00:00");
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mp3List = new ArrayList<String>();
                        File[] listFiles = new File(mp3Path).listFiles();
                        for(File file : listFiles){
                            fileName[0] = file.getName();
                            //extName[0] = fileName[0].substring(fileName[0].length()-3);
                            //if(extName[0].equals((String) "3gp"))
                                mp3List.add(fileName[0]);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MicActivity.this,android.R.layout.simple_list_item_single_choice,mp3List);
                        adapter.notifyDataSetChanged();
                        listViewMP3.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 1000);
            }
        });
    }
    public void onClick(View view){
        Intent intent = new Intent (this,Mp3upload.class);
        startActivity(intent);
    }

}