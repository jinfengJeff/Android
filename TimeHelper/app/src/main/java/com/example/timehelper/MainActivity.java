package com.example.timehelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import com.example.timehelper.util.FetchTimeThread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> {

            while (true) {
                long currentTime = System.currentTimeMillis();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

                Date date = new Date(currentTime);

                String timestampStr = formatter.format(date);

                Message message = new Message();
                message.obj = timestampStr;
                mHandler.sendMessage(message);

            }

        }).start();


        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                TextView textView = findViewById(R.id.my_text);
                textView.setText(msg.obj.toString());
            }
        };

//        new FetchTimeThread().start(); // 获取网络时间（基于淘宝API）

    }
}