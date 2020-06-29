package com.dowellcomputer.naviexam.ETC;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
//import com.google.android.gms.common.api.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.dowellcomputer.naviexam.R;


public class LoginActivity extends AppCompatActivity {
    Button loginbtn;
    private Button idedit;
    private EditText passedit;
    private AlertDialog dialog;
    String cctvURL, GPS, post_title, post_text, public_code2,personal_code2, post2_text, post2_title;
    String[] public_code_list;
    public String[] personal_code_list;
    String[] public_code2_list;
    String[] personal_code2_list;
    public String public_id="";
    public int login_state=0;
    public String personal_id=null;
    public String[] post_title_list;
    public String[] post_text_list;
    public String[] post2_title_list;
    public String[] post2_text_list;
    String[] data_list, name_list;
    public String[] cctv;
    public String[] gps;
    String public_code, personal_code,data, name;
    private int personal_num=3222;
    String toastMessage;
    private static final String TAG = "MAIN";
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).hide();
        // 데이터베이스 값 읽어오기
        new LoginActivity.BackgroundTask3().execute();
        idedit = (Button) findViewById(R.id.idedit);
        passedit = (EditText) findViewById(R.id.passedit);
        loginbtn = (Button) findViewById(R.id.loginbtn);


        idedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                public_code_list = public_code.split("/");
                final int[] selectedIndex = {0};
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                dialog = builder.setTitle("유치원을 선택하세요.")
                        .setSingleChoiceItems(
                                public_code_list,
                                0,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedIndex[0] = which;
                                    }
                                }
                        )
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                public_id = public_code_list[selectedIndex[0]];
                                idedit.setText(public_id);
                            }
                        }).create();
                dialog.show();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_list = data.split("/");
                //personal_code_list = personal_code.split("/");
                personal_id = passedit.getText().toString();
                if (personal_id == null || public_id == "") {
                    public_id = "";
                    idedit.setText(null);
                    passedit.setText(null);
                    personal_id = null;
                    login_state = 0;

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("코드를 확인해주세요.")
                            .setPositiveButton("닫기", null)
                            .create();
                    dialog.show();

                } else {
                    String temp = public_id+"&&"+personal_id;
                    name_list=name.split("/");
                    for(int i=0;i<data_list.length;i++){
                        if(data_list[i].equals(temp)){
                            login_state=1;
                            name=name_list[i];
                        }
                    }
                    if (login_state == 0) {
                        public_id = "";
                        idedit.setText(null);
                        passedit.setText(null);
                        personal_id = null;
                        login_state = 0;

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        dialog = builder.setMessage("코드를 확인해주세요.")
                                .setPositiveButton("닫기", null)
                                .create();
                        dialog.show();

                    } else {
                        new LoginActivity.BackgroundTask().execute();
                        new LoginActivity.BackgroundTask2().execute();
                        Log.d(this.getClass().getName(),"실행 : Back2 "+public_id);
                        new LoginActivity.BackgroundTask4().execute();
                        Log.d(this.getClass().getName(),"실행 : Back4 "+personal_id);

                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("인증 되었습니다.").setCancelable(false)
                                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("GPS", GPS);
                                                Log.d(this.getClass().getName(),"실행 : GPS값 넘기기 "+GPS);
                                                intent.putExtra("cctvURL", cctvURL);
                                                Log.d(this.getClass().getName(),"실행 : cctv값 넘기기 "+cctvURL);
                                                intent.putExtra("title", post_title);
                                                Log.d(this.getClass().getName(),"실행 : 공지 제목 넘기기 "+post_title);
                                                intent.putExtra("text", post_text);
                                                Log.d(this.getClass().getName(),"실행 : 공지 내용 넘기기 "+post_text);
                                                intent.putExtra("title2", post2_title);
                                                Log.d(this.getClass().getName(),"실행 : 알림장 제목 넘기기 "+post2_title);
                                                intent.putExtra("text2", post2_text);
                                                Log.d(this.getClass().getName(),"실행 : 알림장 내용 넘기기 "+post2_text);
                                                intent.putExtra("public_id", public_id);
                                                Log.d(this.getClass().getName(),"실행 : 유치원 넘기기 "+public_id);
                                                intent.putExtra("personal_id", personal_id);
                                                Log.d(this.getClass().getName(),"실행 : 개인 코드 넘기기 "+personal_id);
                                                intent.putExtra("name", name);
                                                Log.d(this.getClass().getName(),"실행 : 이름 넘기기 "+name);
                                                startActivity(intent);
                                            }
                                        })
                                        .create();
                                dialog.show();
                                login_state = 0;
                            }
                        }, 2000);// 딜레이를 준 후 시작
                    }
                }
            }

        });
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

    class BackgroundTask3 extends AsyncTask<Void, Void, String> {
        String target;
        @Override
        protected void onPreExecute() {
            target = "http://jes9401.pythonanywhere.com/web/getUser/"; // 라즈베리파이에서 측정한 HomeData를 받아올 url


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
                public_code = jsonResponse.getString("public_code");
                personal_code = jsonResponse.getString("personal_code");
                data = jsonResponse.getString("data");
                name = jsonResponse.getString("name");
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

}