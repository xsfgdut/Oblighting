package com.ob.obsmarthouse.common.net.cloudnet;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 *  网络交互过程中的处理
 * Created by adolf_dongon 2016/1/6.
 */
public interface HttpRespond {

    void  onRequest();

    List<NameValuePair> getParamter();

    void onSuccess(String json);

    void onFaild(Exception e);

    void onFaild(int state);



}