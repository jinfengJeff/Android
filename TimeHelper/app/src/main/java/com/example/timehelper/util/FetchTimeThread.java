package com.example.timehelper.util;

import static com.example.timehelper.MainActivity.mHandler;

import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchTimeThread extends Thread {
    private String tbTimestampUri = "http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp";

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                //创建okhtto请求的对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //创建请求连接，url里面存放请求连接，get表示其实get请求
                Request request = new Request.Builder().url(tbTimestampUri).get().build();
                try {
                    //使用execute()方法执行请求
                    Response response = okHttpClient.newCall(request).execute();
                    //定义字符串接收请求信息
                    String jstring = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(jstring);
                        Object data = jsonObject.get("data");
                        JSONObject jsonObject1 = new JSONObject(data.toString());
                        String t = jsonObject1.get("t").toString();

                        String formats = "yyyy-MM-dd HH:mm:ss:SSS";
                        Long timestamp = Long.parseLong( t );
                        //日期格式字符串
                        String timestampStr = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
//                        System.out.println("timestampStr:" + timestampStr);

                        Message message = new Message();
                        message.obj = timestampStr;
                        mHandler.sendMessage(message);

                        /**
                         * 转Date
                         */
                        //                    try {
                        //                        DateFormat dateFormat = new SimpleDateFormat(formats);
                        //                        Date date = dateFormat.parse(timestampStr);
                        //                        System.out.println("date:" + date);
                        //                    } catch (ParseException e) {
                        //                        e.printStackTrace();
                        //                    }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
