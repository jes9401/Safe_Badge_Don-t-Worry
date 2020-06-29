package com.dowellcomputer.naviexam.CCTV;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.dowellcomputer.naviexam.R;
import com.dowellcomputer.naviexam.Record.Mp3upload;
import com.kyleduo.switchbutton.SwitchButton;

public class CctvActivity extends AppCompatActivity{
    private AlertDialog dialog;
    private WebView mWebView;
    private String cctvURL;// 접속 URL
    private ImageButton back_btn;
    public int state=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).hide();

        back_btn=(ImageButton)findViewById(R.id.back_btn);
        SwitchButton switchbtn = (SwitchButton)findViewById(R.id.switchbtn);

        Intent intent = getIntent();
        cctvURL = intent.getStringExtra("cctvURL");
        Log.d(this.getClass().getName(),"cctv값 받기"+cctvURL);
        cctvURL = "http://"+cctvURL+":8080/javascript_simple.html"; //mjpg 스트리머를 사용하기 위해 받아온 아이피(cctvURL)을 대입
        mWebView = (WebView) findViewById(R.id.webView);//xml 자바코드 연결


        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        back_btn.setOnClickListener(new View.OnClickListener() {    //뒤로가기 버튼
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    //버튼이 체크 됐을 경우
                if(isChecked){
                    mWebView.loadUrl(cctvURL);//웹뷰 실행
                    mWebView.setWebChromeClient(new WebChromeClient());//웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
                    mWebView.setWebViewClient(new WebViewClientClass());//새창열기 없이 웹뷰 내에서 다시 열기//페이지 이동 원활히 하기위해 사용
                    state=1;
                    if(state==1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CctvActivity.this);
                        dialog = builder.setMessage("CCTV를 켰습니다.")
                                .setPositiveButton("닫기", null)
                                .create();
                        dialog.show();
                    }
                }
                else{
                    mWebView.loadUrl("");   //빈 url 넣어서 웹뷰를 끔
                    state=0;
                }

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//뒤로가기 버튼 이벤트
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {//페이지 이동
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }

}
