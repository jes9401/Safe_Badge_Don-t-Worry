package com.dowellcomputer.naviexam.Announce;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import com.dowellcomputer.naviexam.Announce.NoticeListAdapter;

import com.dowellcomputer.naviexam.Notice.NoticeActivity;
import com.dowellcomputer.naviexam.Notice.NoticeViewActivity;
import com.dowellcomputer.naviexam.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementActivity extends AppCompatActivity  {

    private ImageButton back_btn;
    private AlertDialog dialog;
    private ListView post2_list;
    private String post_title="",post_text="";
    private String personal_id;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    public String[] post_title_list, post_text_list;
    public String title;
    public String temp="";
    public SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).hide();
        post2_list=(ListView)findViewById(R.id.post2_list);
        back_btn=(ImageButton)findViewById(R.id.back_btn);
        Intent intent = getIntent();
        post_title = intent.getStringExtra("title2");
        post_text = intent.getStringExtra("text2");
        personal_id = intent.getStringExtra("personal_id");
        Log.d(this.getClass().getName(),"실행 : 개인번호 넘기기 "+personal_id);

        post_title_list = post_title.split(",");
        post_text_list = post_text.split(",");
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(),noticeList);
        for(int i=post_title_list.length-1;i>=0;i--){
            noticeList.add(new Notice(post_title_list[i],post_text_list[i]));
        }
        post2_list.setAdapter(adapter);
        post2_list.getChildAt(2);
        swipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.Swipe);

        post2_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                for (int i = 0; i < (post_text_list.length)/2 ; i++) {
                    int max = post_text_list.length- i -1;
                    temp = post_text_list[i];
                    post_text_list[i] = post_text_list[max];
                    post_text_list[max] = temp;
                }
                for (int i = 0; i < (post_title_list.length)/2 ; i++) {
                    int max = post_title_list.length- i -1;
                    temp = post_title_list[i];
                    post_title_list[i] = post_title_list[max];
                    post_title_list[max] = temp;
                }
                String title = (String)adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), NoticeViewActivity.class);
                for(int i=0;i<post_text_list.length;i++){
                    if(id==i){
                        intent.putExtra("text",post_text_list[i]);
                        intent.putExtra("title",post_title_list[i]);
                    }
                }
                startActivity(intent);
            }
        });



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AnnouncementActivity.BackgroundTask().execute();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        post_title_list = post_title.split(",");
                        post_text_list = post_text.split(",");

                        noticeList = new ArrayList<Notice>();
                        adapter = new NoticeListAdapter(getApplicationContext(),noticeList);
                        for(int i=post_title_list.length-1;i>=0;i--){
                            noticeList.add(new Notice(post_title_list[i],post_text_list[i]));
                        }
                        post2_list.setAdapter(adapter);
                        post2_list.getChildAt(2);
                        post2_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> adapterView,
                                                    View view, int position, long id) {
                                for (int i = 0; i < (post_text_list.length)/2 ; i++) {
                                    int max = post_text_list.length- i -1;
                                    temp = post_text_list[i];
                                    post_text_list[i] = post_text_list[max];
                                    post_text_list[max] = temp;
                                }
                                for (int i = 0; i < (post_title_list.length)/2 ; i++) {
                                    int max = post_title_list.length- i -1;
                                    temp = post_title_list[i];
                                    post_title_list[i] = post_title_list[max];
                                    post_title_list[max] = temp;
                                }
                                String title = (String) adapterView.getItemAtPosition(position);
                                Intent intent = new Intent(getApplicationContext(), NoticeViewActivity.class);
                                for (int i = 0; i < post_text_list.length; i++) {
                                    if (id == i) {
                                        intent.putExtra("text",post_text_list[i]);
                                        intent.putExtra("title",post_title_list[i]);
                                    }
                                }
                                startActivity(intent);

                            }
                        });

                        swipeRefreshLayout.setRefreshing(false);
                    }},2000);
            }
        });


    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
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
                post_title= jsonResponse.getString("title2");
                post_text= jsonResponse.getString("text2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



