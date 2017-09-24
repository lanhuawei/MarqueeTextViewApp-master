package com.example.administrator.marqueetextviewapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MarqueeTextView marqueeTv;
    private String [] textArrays = new String[]{"this is content No.1","this is content No.2","this is content No.3"};

    private ArrayList<String> titleList = new ArrayList<String>(); // 上下滚动消息栏主题
    private ArrayList<String> linkUrlArray = new ArrayList<String>(); // 滚动消息栏内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marqueeTv = (MarqueeTextView) findViewById(R.id.marqueeTv);

        titleList.add("this is content No.1");
        titleList.add("this is content No.2");


        marqueeTv.setTextArraysAndClickListener(titleList, new MarqueeTextViewClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,AnotherActivity.class));
                String title = titleList.get(marqueeTv.getmCurrPos());
                if (("this is content No.1").equals(title)) {
                    Intent intent = new Intent(MainActivity.this, AnotherActivity.class);
                    startActivity(intent);
                } else if (("this is content No.2").equals(title)) {
                    Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        marqueeTv.releaseResources();
        super.onDestroy();
    }
}
