package com.ob.obsmarthouse.common.util;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.net.cloudnet.GetParameter;
import com.ob.obsmarthouse.common.net.cloudnet.HttpRequst;
import com.ob.obsmarthouse.common.net.cloudnet.HttpRespond;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 检查当前网络判断工作模式，执行服务器连接请求
 * Created by adolf_dong on 2016/5/19.
 */
public class NetUtil {



    private static final String TAG = "NetUtil";



    private static int cloudModeDetial;
    private static int workMode;
    private DhcpInfo dhcpinfo;
    private DatagramPacket mDatagramPacket;
    private MulticastSocket ms;
    private int search_count;
    private boolean isSearchTimeout;
    private DatagramPacket pack;
    private List<String> oboxStringList;

    /**
     * 检测当前网络环境
     *
     * @param context 上下文
     * @return OBConstant.ON_AP ap模式 OBConstant.ON_STATION station模式
     */
    public int ckNet(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager.isWifiEnabled()) {
            dhcpinfo = mWifiManager.getDhcpInfo();
            int ip = dhcpinfo.serverAddress;
            if ((ip & 0xff) == 192 && ((ip >> 8) & 0xff)
                    == 168 && ((ip >> 16) & 0xff) == 2 &&
                    ((ip >> 24) & 0xff) == 3) {
                setWorkMode(OBConstant.NetState.ON_AP);
            } else {
                setWorkMode(OBConstant.NetState.ON_STATION);
            }
        } else {
            Toast.makeText(context, R.string.check_wifi_tips, Toast.LENGTH_SHORT).show();
        }
        return workMode;
    }

    /**
     * 发送udp广播包
     *
     * @param handler 接收结束后此handler回调
     */
    public void udpBc(final Handler handler) {
        try {
            if (ms == null) {
                ms = new MulticastSocket();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String broadCastIp = null;
                int ip = dhcpinfo.serverAddress;
                if (ip != 0) {
                    broadCastIp = ((ip & 0xff) + "." + (ip >> 8 & 0xff) + "." + (ip >> 16 & 0xff) + "." + "255");
                }
                byte[] buffer = new byte[10];
                mDatagramPacket = new DatagramPacket(buffer, 10);
                String str = "HLK";
                byte out[] = str.getBytes();
                mDatagramPacket.setData(out);
                mDatagramPacket.setLength(out.length);
                mDatagramPacket.setPort(988);
                search_count = 0;
                while (search_count < 5) {
                    try {
                        InetAddress address = InetAddress.getByName(broadCastIp);
                        mDatagramPacket.setAddress(address);
                        ms.send(mDatagramPacket);
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    search_count++;
                }
                Timer mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isSearchTimeout = true;
                        Message mes = new Message();
                        mes.what = OBConstant.NetState.ON_DSFINISH;
                        handler.sendMessage(mes);
                    }
                }, 3000);
            }
        }).start();
    }


    /**
     * 接收udp广播包
     *
     * @param obIplist 存放oboxip地址的列表
     */
    public void dscvObox(List<String> obIplist) {
        oboxStringList = obIplist;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!isSearchTimeout) {
                        byte[] DataReceive = new byte[64];
                        pack = new DatagramPacket(DataReceive, DataReceive.length);
                        ms.receive(pack);
                        String addr = ("" + pack.getAddress()).substring(1);
                        Log.i(TAG, "brocastrecieve" + addr);
                        boolean isFound = false;
                        for (String tmp : oboxStringList) {
                            if (tmp.equalsIgnoreCase(addr)) {
                                isFound = true;
                                break;
                            }
                        }
                        if (!isFound) {
                            oboxStringList.add(addr);
                            Log.d(TAG, "add(addr) = " + addr);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 发起网络请求
     *
     * @param respond HttpRespond的实现实例
     * @param action  请求的命令
     */
    public static void doCloudEven(HttpRespond respond, String action) {
        GetParameter.ACTION = action;
        HttpRequst.getHttpRequst().setInstance(respond);
        HttpRequst.getHttpRequst().request();
    }


    /**
     * @return 获得当前向服务器请求执行的流程cmd
     */
    public static String getCloudEvenTag() {
        return GetParameter.ACTION;
    }

    /**
     * 设置当前工作模式
     *
     * @param mode OBConstant.ON_AP ap模式 OBConstant.ON_STATION station模式 OBConstant.ON_CLOUD服务器模式
     */
    public static void setWorkMode(int mode) {
        workMode = mode;
    }

    public static int getWorkMode() {
        return workMode;
    }

    /**详细的登录者类型
     * @param detialMode
     * {@link com.ob.obsmarthouse.common.constant.OBConstant.CloudDitalMode#SUPERROOT}
     * {@link com.ob.obsmarthouse.common.constant.OBConstant.CloudDitalMode#ROOT}
     * {@link com.ob.obsmarthouse.common.constant.OBConstant.CloudDitalMode#ADMIN}
     * {@link com.ob.obsmarthouse.common.constant.OBConstant.CloudDitalMode#GUEST}
     */
    public static void setCloudModeDetial(int detialMode) {
        cloudModeDetial = detialMode;
    }

    public static int getCloudModeDetial() {

        return cloudModeDetial;

    }


}
