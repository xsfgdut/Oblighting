package com.ob.obsmarthouse.common.aplction;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.ob.obsmarthouse.common.net.localnet.Respond;
import com.ob.obsmarthouse.common.net.localnet.TcpSend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局application
 * Created by adolf_dong on 2016/5/5.
 */
public class SmartApp extends Application {
    private Map<String, TcpSend> tcpSends = new HashMap<>();
    private List<Respond> responds = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressWarnings("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (Respond respond :
                    responds) {
                respond.onReceive(msg);
            }
        }
    };

    public Map<String, TcpSend> getTcpSends() {
        return tcpSends;
    }

    /**
     * 以tcpSend的obox名称为键添加到映射
     *
     * @param tcpSend 目标tcpSend连接
     */
    public void addTcpSend(TcpSend tcpSend) {
        if (tcpSend != null && tcpSend.getOboxName() != null && !tcpSend.getOboxName().equals("")) {
            tcpSends.put(tcpSend.getOboxName(), tcpSend);
        }
    }

    public TcpSend getTcpSend(String key) {
        return tcpSends.get(key);
    }

    public void clearTcpSends() {
        tcpSends.clear();
    }


    /**
     * 给实现HandlerListner接口的页面设置监听
     *
     * @param respond HandlerListner接口的引用
     */
    public void regist(Respond respond) {
        if (!responds.contains(respond)) {
            responds.add(respond);
        }
    }

    /**
     * 给实现HandlerListner接口的页面移除监听
     *
     * @param respond HandlerListner接口的引用
     */
    public void unRegist(Respond respond) {
        if (responds.contains(respond)) {
            responds.remove(respond);
        }
    }

    public Handler getHandler() {
        return handler;
    }
}
