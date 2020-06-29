package com.dowellcomputer.naviexam.Notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dowellcomputer.naviexam.R;

public class NoticeViewActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private TextView title_view, text_view;
    ScrollView scroll;
    private String post_title="",post_text="";
    public String[] post_title_list, post_text_list;
    public String title,text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view);
        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).hide();
        title_view = (TextView)findViewById(R.id.title_view);
        text_view = (TextView)findViewById(R.id.text_view);

        scroll = (ScrollView)findViewById(R.id.scroll);
        scroll.fullScroll(View.FOCUS_DOWN);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        text = intent.getStringExtra("text");
        title_view.setText(title);
        text_view.setText(text);
    }
}
