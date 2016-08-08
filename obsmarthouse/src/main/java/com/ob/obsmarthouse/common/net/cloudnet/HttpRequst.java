package com.ob.obsmarthouse.common.net.cloudnet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ob.obsmarthouse.common.constant.CloudConstant;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpRequst {
    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 4, 20, TimeUnit.SECONDS, queue);
    private HttpRespond httpRespond;
    private HttpRespond backUphttpRespond;
    private static HttpRequst httpRequst;
    private String TAG = "HttpRequst";
    @SuppressWarnings("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case  CloudConstant.ResponState.State_OK:
                    String json = msg.getData().getString(CloudConstant.ResultKey.KEY);
                    httpRespond.onSuccess(json);
                    break;
                case CloudConstant.ResponState.State_F:
                    int stateCode = msg.getData().getInt(CloudConstant.ResultKey.KEY);
                    httpRespond.onFaild(stateCode);
                    break;
                case CloudConstant.ResponState.State_E:
                    IOException e = (IOException) msg.getData().getSerializable(CloudConstant.ResultKey.KEY);
                    httpRespond.onFaild(e);
                    break;

            }
        }
    };



    public static HttpRequst getHttpRequst() {
        if (httpRequst == null) {
            synchronized (HttpRequst.class) {
                if (httpRequst == null) {
                    httpRequst = new HttpRequst();
                }
            }
        }
        return httpRequst;
    }

    private HttpRequst() {
    }

    public void setInstance(HttpRespond httpRespond) {
        backUphttpRespond = this.httpRespond;
        this.httpRespond = httpRespond;
    }

    public  void restore(){
        this.httpRespond =backUphttpRespond;
    }

    public void request() {
        httpRespond.onRequest();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(CloudConstant.Source.Common);
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(httpRespond.getParamter()));
                    Log.i(TAG, httpRespond.getParamter().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                try {
                    HttpResponse response = httpclient.execute(httpPost);
                    int stateCode = response.getStatusLine().getStatusCode();
                    if (stateCode == HttpStatus.SC_OK) {
                        String json = EntityUtils.toString(response.getEntity());
                        message.what = CloudConstant.ResponState.State_OK;
                        bundle.putString(CloudConstant.ResultKey.KEY, json);
                        Log.i(TAG, json);

                    } else {
                        message.what = CloudConstant.ResponState.State_F;
                        bundle.putInt(CloudConstant.ResultKey.KEY, stateCode);
                    }
                } catch (IOException e) {
                    message.what = CloudConstant.ResponState.State_E;
                    bundle.putSerializable(CloudConstant.ResultKey.KEY, e);
                    e.printStackTrace();
                }
                message.setData(bundle);
                handler.sendMessage(message);
            }

        });
    }


}