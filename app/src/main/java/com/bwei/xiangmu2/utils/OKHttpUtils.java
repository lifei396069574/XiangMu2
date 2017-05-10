package com.bwei.xiangmu2.utils;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：李飞 on 2017/5/10 14:10
 * 类的用途：
 */

public class OKHttpUtils {

    private OkHttpClient client;
    //超时时间
    public static final int TIMEOUT=1000*60;

    private Handler handler = new Handler(Looper.getMainLooper());

    public OKHttpUtils() {
        this.init();
    }

    private void init() {
        client=new OkHttpClient();

        //设置超时
        client.newBuilder().
                connectTimeout(TIMEOUT, TimeUnit.SECONDS).
                writeTimeout(TIMEOUT,TimeUnit.SECONDS).
                readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * post 异步请求
     *
     */
    public void postT(String url,final HttpCallBack callBack){

        FormBody body = new FormBody.Builder()
                .add("page", "1")
                .add("code", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        OnStart(callBack);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callBack, response.body().string());
                } else {
                    OnError(callBack, response.message());
                }
            }
        });
    }
    /**
     * post   同步请求
     *
     * @param url
     * @param callBack
     */
    public void postAsy(final String url, final HttpCallBack callBack){
        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("page", "1")
                        .add("code", "news")
                        .add("pageSize", "20")
                        .add("parentid", "0")
                        .add("type", "1")
                        .build();
                Request.Builder builder = new Request.Builder().url(url);
                Request request = builder
                        .post(body)
                        .build();
                OnStart(callBack);
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        OnError(callBack,e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            onSuccess(callBack,response.body().string());
                        }else{
                            OnError(callBack, response.message());
                        }
                    }
                });
            }
        }.start();


    }
    /**
     * get 同步请求
     *
     * @param url
     * @param callBack
     */
    public void getT(final String url, final HttpCallBack callBack){
        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder().url(url);
                builder.method("GET",null);
                Request request = builder.build();
                OnStart(callBack);
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        OnError(callBack,e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            onSuccess(callBack,response.body().string());
                        }else{
                            OnError(callBack,response.message());
                        }
                    }
                });
            }
        }.start();


    }
    /**
     * get 异步请求
     *
     * @param url
     * @param callBack
     */
    public void getAsy(String url,final HttpCallBack callBack){
        Request request = new Request.Builder()
                .url(url)
                .build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack,e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    onSuccess(callBack,response.body().string());
                }else{
                    OnError(callBack,response.message());
                }
            }
        });
    }
    public void OnStart(HttpCallBack callBack){
        if(callBack!=null){
            callBack.onstart();
        }
    }
    public void onSuccess(final HttpCallBack callBack,final String data){
        if(callBack!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {//在主线程操作
                    callBack.onSusscess(data);
                }
            });
        }
    }
    public void OnError(final HttpCallBack callBack,final String msg){
        if(callBack!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onError(msg);
                }
            });
        }
    }
    public static abstract class HttpCallBack{
        //开始
        public void onstart(){};
        //成功回调
        public abstract void onSusscess(String data);
        //失败
        public void onError(String meg){};
    }
}
