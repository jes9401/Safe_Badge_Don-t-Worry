package com.dowellcomputer.naviexam.ETC;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dowellcomputer.naviexam.Announce.AnnouncementActivity;
import com.dowellcomputer.naviexam.CCTV.CctvActivity;
import com.dowellcomputer.naviexam.GPS.GpsActivity;
import com.dowellcomputer.naviexam.Notice.NoticeActivity;
import com.dowellcomputer.naviexam.R;
import com.dowellcomputer.naviexam.Record.MicActivity;
import com.dowellcomputer.naviexam.Record.RecordActivity;
import com.dowellcomputer.naviexam.Record.Record_helper;
import com.dowellcomputer.naviexam.SOS.SosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

//import static com.dowellcomputer.naviexam.ETC.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton gpsimgbtn,cctvimgbtn,sosimgbtn,micimgbtn;
    private EditText editTextTitle;
    private EditText editTextMessage;
    private String cctvURL;
    private String GPS;
    private String post_title;
    private String post_text;
    private String post2_title;
    private String post2_text;
    private String temp;
    public int state=0;
    private String public_id;
    private String personal_id;
    private String name;
    private String info;
    private String pushdata="";
    private long backKeyPressedTime = 0;
    public int a=0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        toolbar.setBackgroundColor(Color.parseColor("#FFF2CD"));
        setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(310);



        editTextTitle= findViewById(R.id.edit_text_title);
        editTextMessage= findViewById(R.id.edit_text_message);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        GPS = intent.getStringExtra("GPS");
        cctvURL = intent.getStringExtra("cctvURL");
        post_title = intent.getStringExtra("title");
        post_text = intent.getStringExtra("text");
        post2_title = intent.getStringExtra("title2");
        post2_text = intent.getStringExtra("text2");
        public_id = intent.getStringExtra("public_id");
        personal_id = intent.getStringExtra("personal_id");
        name = intent.getStringExtra("name");

        View headerView = navigationView.getHeaderView(0);
        TextView infotext = (TextView) headerView.findViewById(R.id.infotext);
        info=name+","+public_id;
        infotext.setText(info);
        gpsimgbtn = (ImageButton) findViewById(R.id.gpsimgbtn);
        sosimgbtn = (ImageButton) findViewById(R.id.sosimgbtn);
        micimgbtn = (ImageButton) findViewById(R.id.micimgbtn);
        cctvimgbtn = (ImageButton) findViewById(R.id.cctvimgbtn);


        cctvimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CctvActivity.class);
                intent.putExtra("cctvURL", cctvURL);       //cctv 정보를 넘겨줌
                startActivity(intent);
            }
        });
        gpsimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GpsActivity.class);
                intent.putExtra("GPS", GPS);
                intent.putExtra("personal_id", personal_id);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        sosimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SosActivity.class);
                intent.putExtra("cctvURL", cctvURL);
                intent.putExtra("GPS", GPS);
                startActivity(intent);
            }
        });
        micimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MicActivity.class);
                startActivity(intent);

            }
        });
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
        TimerTask adTast = new TimerTask() {
            public void run() {
                new MainActivity.BackgroundTask().execute();
                new MainActivity.BackgroundTask2().execute();
                new MainActivity.BackgroundTask4().execute();
                new MainActivity.BackgroundTask5().execute();
                Log.e("adTast ", "timer");
                Log.d("adTast",pushdata);
                if (pushdata.equals(temp)){
                    a=1;
                }
                else if(temp==""){
                    a=1;
                }
                else if(pushdata==""){
                    a=1;
                }
                else if (temp!= "" && temp != pushdata &&pushdata.equals("1")) {
                    Log.d("adTast",a+"haha");
                    Log.d("adTast",temp+"/////////"+pushdata);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
                    NotificationCompat.Builder builder = null;

                    //Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        String channelID = "channel_01"; //알림채널 식별자
                        String channelName = "MyChannel01"; //알림채널의 이름(별명)

                        //알림채널 객체 만들기
                        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_UNSPECIFIED);

                        //알림매니저에게 채널 객체의 생성을 요청
                        notificationManager.createNotificationChannel(channel);

                        //알림건축가 객체 생성
                        builder = new NotificationCompat.Builder(MainActivity.this, channelID);


                    } else {
                        //알림 건축가 객체 생성
                        builder = new NotificationCompat.Builder(MainActivity.this, null);
                    }

                    //건축가에게 원하는 알림의 설정작업
                    builder.setSmallIcon(android.R.drawable.ic_dialog_email);

                    //상태바를 드래그하여 아래로 내리면 보이는
                    //알림창(확장 상태바)의 설정
                    builder.setContentTitle(name);//알림창 제목
                    builder.setContentText("Messages....");//알림창 내용
                    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle(builder);
                    bigTextStyle.bigText("탑승했습니다!"); //줄바꿈은 안되는 폰도 있음.
                    //알림창의 큰 이미지
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.jolrinbecz);
                    builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.

                    //알림창을 클릭시에 실행할 작업(SecondActivity 실행) 설정
                    // ####Intent intent2 = new Intent(this, LoginActivity.class);
                    //지금 실행하는 것이 아니라 잠시 보류시키는 Intent 객체 필요
                    // ####PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    // ####builder.setContentIntent(pendingIntent);

                    //알림창 클릭시에 자동으로 알림제거
                    builder.setAutoCancel(true);

                    //그밖의 설정들...

                    //알림 사운드 설정
                    /*Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
                    soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gaho_start); //내가 가져온 음악파일을 넣어준다.
                    builder.setSound(soundUri);*/

                    //알림 진동 설정[진동은 반드시 퍼미션 추가 필요]
                    builder.setVibrate(new long[]{0, 2000, 1000, 3000});// 0초 대기, 2초 진동, 1초 대기, 3초 진동

                    //잘 사용하지 않는 그밖의 설정들...
                    //builder.addAction(android.R.drawable.ic_menu_more,"more", pendingIntent);
                    //builder.addAction(android.R.drawable.ic_menu_set_as,"setting", pendingIntent);

                    builder.setColor(Color.MAGENTA);

                    //요즘들어 종종 보이는 알림창 스타일
                    //1. BigpictureStyle
                    //NotificationCompat.BigPictureStyle pictureStyle = new NotificationCompat.BigPictureStyle(builder);
                    //pictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.gametitle_09));

                    //2. BigTextStyle


                    ///3. InboxStyle
                    //NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle(builder);
                    //inboxStyle.addLine("first");
                    //inboxStyle.addLine("second");
                    //inboxStyle.addLine("hello--");
                    //inboxStyle.addLine("안녕하세요.");

                    //상태표시줄 표시
                    //builder.setProgress(100, 50, true); //마지막을 false로 쓰면 게이지가 멈춰 있음.

                    //건축가에게 알림 객체 생성하도록
                    Notification notification = builder.build();

                    //알림매니저에게 알림(Notify) 요청
                    notificationManager.notify(1, notification);

                    //알림 요청시에 사용한 번호를 알림제거 할 수 있음.
                    //notificationManager.cancel(1);

                }
                else if (temp!= "" && temp != pushdata &&pushdata.equals("0")) {
                    Log.d("adTast",a+"haha");
                    Log.d("adTast",temp+"/////////"+pushdata);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
                    NotificationCompat.Builder builder = null;

                    //Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        String channelID = "channel_01"; //알림채널 식별자
                        String channelName = "MyChannel01"; //알림채널의 이름(별명)

                        //알림채널 객체 만들기
                        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_UNSPECIFIED);

                        //알림매니저에게 채널 객체의 생성을 요청
                        notificationManager.createNotificationChannel(channel);

                        //알림건축가 객체 생성
                        builder = new NotificationCompat.Builder(MainActivity.this, channelID);


                    } else {
                        //알림 건축가 객체 생성
                        builder = new NotificationCompat.Builder(MainActivity.this, null);
                    }

                    //건축가에게 원하는 알림의 설정작업
                    builder.setSmallIcon(android.R.drawable.ic_dialog_email);

                    //상태바를 드래그하여 아래로 내리면 보이는
                    //알림창(확장 상태바)의 설정
                    builder.setContentTitle(name);//알림창 제목
                    builder.setContentText("Messages....");//알림창 내용
                    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle(builder);
                    bigTextStyle.bigText("하차했습니다!"); //줄바꿈은 안되는 폰도 있음.
                    //알림창의 큰 이미지
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.jolrinbecz);
                    builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.

                    //알림창을 클릭시에 실행할 작업(SecondActivity 실행) 설정
                    // ####Intent intent2 = new Intent(this, LoginActivity.class);
                    //지금 실행하는 것이 아니라 잠시 보류시키는 Intent 객체 필요
                    // ####PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    // ####builder.setContentIntent(pendingIntent);

                    //알림창 클릭시에 자동으로 알림제거
                    builder.setAutoCancel(true);

                    //그밖의 설정들...

                    //알림 사운드 설정
                    /*Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
                    soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gaho_start); //내가 가져온 음악파일을 넣어준다.
                    builder.setSound(soundUri);*/

                    //알림 진동 설정[진동은 반드시 퍼미션 추가 필요]
                    builder.setVibrate(new long[]{0, 2000, 1000, 3000});// 0초 대기, 2초 진동, 1초 대기, 3초 진동

                    //잘 사용하지 않는 그밖의 설정들...
                    //builder.addAction(android.R.drawable.ic_menu_more,"more", pendingIntent);
                    //builder.addAction(android.R.drawable.ic_menu_set_as,"setting", pendingIntent);

                    builder.setColor(Color.MAGENTA);

                    //요즘들어 종종 보이는 알림창 스타일
                    //1. BigpictureStyle
                    //NotificationCompat.BigPictureStyle pictureStyle = new NotificationCompat.BigPictureStyle(builder);
                    //pictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.gametitle_09));

                    //2. BigTextStyle


                    ///3. InboxStyle
                    //NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle(builder);
                    //inboxStyle.addLine("first");
                    //inboxStyle.addLine("second");
                    //inboxStyle.addLine("hello--");
                    //inboxStyle.addLine("안녕하세요.");

                    //상태표시줄 표시
                    //builder.setProgress(100, 50, true); //마지막을 false로 쓰면 게이지가 멈춰 있음.

                    //건축가에게 알림 객체 생성하도록
                    Notification notification = builder.build();

                    //알림매니저에게 알림(Notify) 요청
                    notificationManager.notify(1, notification);

                    //알림 요청시에 사용한 번호를 알림제거 할 수 있음.
                    //notificationManager.cancel(1);

                }
                temp=pushdata;
            }
        };Timer timer = new Timer();
        timer.schedule(adTast, 0, 5000); // 0초후 첫실행, 3초마다 계속실행

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_notice:
                Intent intent2 = new Intent(getApplicationContext(), NoticeActivity.class);
                intent2.putExtra("title", post_title);
                intent2.putExtra("text", post_text);
                intent2.putExtra("public_id",public_id);
                startActivity(intent2);
                break;
            case R.id.nav_advice_note:
                Intent intent3 = new Intent(getApplicationContext(),AnnouncementActivity.class);
                intent3.putExtra("title2", post2_title);
                intent3.putExtra("text2", post2_text);
                intent3.putExtra("personal_id",personal_id);
                startActivity(intent3);
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("앱을 종료하시겠습니까?").setMessage("선택하세요.").setCancelable(true);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu) ;

        return true ;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("로그아웃 하시겠습니까??").setMessage("선택하세요.").setCancelable(true);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        state = 1;
                        if (state == 1) ;
                        {
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("로그아웃 됐습니다...")
                                    .create();
                            builder.show();
                        }
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }

                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.

            default:
                return super.onOptionsItemSelected(item);
        }
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
                cctvURL = jsonResponse.getString("cctvURL"); // 서버로부터 cctvURL을 받아옴
                GPS = jsonResponse.getString("gps"); //gps 값
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask2 extends AsyncTask<Void, Void, String> {
        String target2;
        @Override
        protected void onPreExecute() {
            target2 = "http://jes9401.pythonanywhere.com/blog/"+public_id;

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target2);
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
                post_title= jsonResponse.getString("title");
                post_text= jsonResponse.getString("text");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask4 extends AsyncTask<Void, Void, String> {
        String target2;
        @Override
        protected void onPreExecute() {
            target2 = "http://jes9401.pythonanywhere.com/blog/getPost/"+personal_id;
            Log.d(this.getClass().getName(),"실행 결과 : "+target2);

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target2);
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
                post2_title= jsonResponse.getString("title2");
                post2_text= jsonResponse.getString("text2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*public void clickBtn(View view){


    }*/
    class BackgroundTask5 extends AsyncTask<Void, Void, String> {
        String target2;
        @Override
        protected void onPreExecute() {
            target2 = "http://jes9401.pythonanywhere.com/rfid/"+personal_id;

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target2);
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
                pushdata= jsonResponse.getString("state");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

