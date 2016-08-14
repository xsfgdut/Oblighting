package com.ob.obsmarthouse.common.net.cloudnet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.util.CloudParseUtil;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
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
            httpRespond.onRespond();
            switch (what) {
                case CloudConstant.ResponState.State_OK:
                    String json = msg.getData().getString(CloudConstant.ResultKey.KEY);
                    if (!CloudParseUtil.isSucceful(json)) {
                        httpRespond.operationFailed(json);
                        return;
                    }
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

    public void restore() {
        this.httpRespond = backUphttpRespond;
    }

    public void request() {
        httpRespond.onRequest();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(CloudConstant.Source.Common);
                try {
                    List<NameValuePair> nameValuePairs = httpRespond.getParamter();
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    Log.i(TAG, nameValuePairs.toString());
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

    /**
     * 上传图片
     *
     * @param filePath    图片filepath
     */
    public void httpPostImage(final String filePath) {
        httpRespond.onRequest();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(CloudConstant.Source.Common);
                MultipartEntity mt = new MultipartEntity();
                File file = new File(filePath);
                FileBody fb = new FileBody(file);
                mt.addPart("file", fb);
                httpPost.setEntity(mt);
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

    /**
     * @param url
     */
    public void getSourceFromNet(final String url,final String filePath,final ImageView imageView) {
        httpRespond.onRequest();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                try {
                    HttpResponse response = client.execute(get);
                    int stateCode = response.getStatusLine().getStatusCode();
                    if (stateCode == HttpStatus.SC_OK) {
                        InputStream is = response.getEntity().getContent();
//                        FileOutputStream fos = new FileOutputStream(filePath);
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                        message.what = CloudConstant.ResponState.State_OK;
                    } else {
                        message.what = CloudConstant.ResponState.State_F;
                        bundle.putInt(CloudConstant.ResultKey.KEY, stateCode);
                    }
                } catch (IOException e) {
                    message.what = CloudConstant.ResponState.State_E;
                    bundle.putSerializable(CloudConstant.ResultKey.KEY, e);
                    e.printStackTrace();
                }
            }
    });
    }
}