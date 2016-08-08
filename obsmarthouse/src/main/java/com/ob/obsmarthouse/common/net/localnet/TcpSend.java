package com.ob.obsmarthouse.common.net.localnet;

import android.content.Context;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.bean.localbean.ObScene;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.util.NetLock;
import com.ob.obsmarthouse.common.util.Sbox;
import com.ob.obsmarthouse.common.util.Transformation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 发送类
 * Created by adolf_dong on 2016/5/13.
 */
public class TcpSend {


    private static final String TAG = "TcpSend";

    private byte[] psw = {0x38, 0x38, 0x38, 0x38, 0x38, 0x38, 0x38, 0x38};

    private String mIp = "192.168.2.3";

    private Socket mSocket;

    private Handler mHandler;

    private TcpReceive mRecive;

    /**
     * 加密方式
     */
    private int encryptionType;
    private String oboxName;
    private String PSW;


    /**
     * 设置当前连接的名称，ps获取后设置
     */
    public void setOboxName(String oboxName) {
        this.oboxName = oboxName;
    }

    public String getOboxName() {
        return oboxName;
    }

    public void setEncryptionType(int encryptionType) {
        this.encryptionType = encryptionType;
    }

    public void setPSW(String psw) {
        this.PSW = psw;
        try {
            this.psw = psw.getBytes(OBConstant.StringKey.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static int[] index = new int[64];

    static {
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }
    }

    /**
     * @return String密码
     */
    public String getPSW() {
        return PSW;
    }

    public byte[] getPsw() {
        return psw;
    }

    public TcpSend(String ip, Handler handler) {
        mSocket = new Socket();
        mIp = ip;
        mHandler = handler;
    }

    public TcpSend(Handler handler) {
        mSocket = new Socket();
        mHandler = handler;
    }

    private void sendCMD(byte[] cmd) {
        OutputStream out;
        try {
            out = mSocket.getOutputStream();
            NetLock.setSrc(cmd);
            byte[] goal = Sbox.pack(cmd, psw, encryptionType);
            out.write(goal);
            Log.d(TAG, "send: " + Transformation.byteArryToHexString(goal));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ip courrent socket's ip address
     */
    public void setIp(String ip) {
        mIp = ip;
    }

    /**
     * @return courrent socket's ip address
     */
    public String getIp() {
        return mIp;
    }


    /**
     * connect
     *
     * @return connect successful
     */
    public boolean connect() {
        SocketAddress mSocketAddress = new InetSocketAddress(mIp, 5000);
        boolean isCon = false;
        try {
            mSocket.connect(mSocketAddress, 20000);
            mRecive = new TcpReceive(mSocket, mHandler);
            mRecive.start();
            isCon = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isCon;
    }

    /**
     * disConnect current connect
     */
    public void disConnect() {
        try {
            mSocket.shutdownInput();
            mSocket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecive.disConnect();
    }

    /**
     * 通知OBOX返回SSID
     */
    public void reqOboxSSID() {
        byte[] cmd = new byte[62];
        cmd[5] = 0x01;
        cmd[6] = 0x02;
        cmd[7] = 0x02;
        cmd[8] = (byte) 0xff;
        cmd[29] = 0x55;
        sendCMD(cmd);
    }

    /**
     * 修改rf密码
     */
    public void changeOboxRfPsw(byte[] oldPsw, byte[] newPsw) {
        byte[] cmd = new byte[62];
        cmd[4] = (byte) 0x80;
        cmd[5] = 0x07;
        cmd[6] = 0x03;//长度
        System.arraycopy(oldPsw, 0, cmd, 7, oldPsw.length);
        System.arraycopy(newPsw, 0, cmd, 7 + oldPsw.length, newPsw.length);
        cmd[61] = 0x55;
        sendCMD(cmd);
    }

    /**
     * 设置OBox工作方式为station模式
     *
     * @param ssid    WIFI名称
     * @param pswdata WIFI密码
     */
    public void setOboxToStation(byte[] ssid, byte[] pswdata) {
        byte[] cmd = new byte[62];
        cmd[4] = (byte) 0x80;
        cmd[5] = 0x13;
        cmd[6] = 0x03;//长度
        cmd[7] = 0x03;
        cmd[8] = 0x0b;
        cmd[9] = 0x01;//obox编号
        cmd[10] = 0x01;//参数编号
        cmd[11] = 0x01;//参数长度
        cmd[12] = 0x01;//工作模式1station  2ap
        cmd[13] = 0x12;
        cmd[14] = (byte) ssid.length;
        System.arraycopy(ssid, 0, cmd, 15, ssid.length);
        cmd[15 + ssid.length] = 0x13;
        cmd[16 + ssid.length] = (byte) pswdata.length;
        System.arraycopy(pswdata, 0, cmd, 17 + ssid.length, pswdata.length);
        //此处可能要加分隔符
        cmd[61] = 0x55;
        sendCMD(cmd);
    }


    /**
     * 获取单节点或者组节点
     *
     * @param index   编号
     * @param isGroup 是否取组
     */
    public void getDevice(int index, boolean isGroup) {
        byte[] cmd = new byte[62];
        cmd[4] = (byte) 0x80;
        cmd[5] = 0x13;
        cmd[7] = 0x03;
        cmd[8] = (byte) (isGroup ? 0x04 : 0x02);
        cmd[9] = (byte) index;
        cmd[61] = 0x55;
        sendCMD(cmd);
    }


    /**
     * 获取节点状态
     *
     * @param cplAddr  节点完整地址
     * @param datamark 数据标识
     */
    public void getDeviceState(byte[] cplAddr, byte[] datamark) {
        byte[] cmd = new byte[62];
        cmd[4] = (byte) 0x01;
        System.arraycopy(cplAddr, 0, cmd, 7, cplAddr.length);
        if (datamark != null) {
            System.arraycopy(datamark, 0, cmd, 7 + cplAddr.length, datamark.length);
        }
        cmd[61] = 0x55;
        sendCMD(cmd);
    }

    /**
     * 获取obox的信息
     */
    public void reqOboxMsg() {
        byte[] cmd = new byte[62];
        cmd[4] = (byte) 0x80;
        cmd[5] = 0x13;
        cmd[7] = 0x03;
        cmd[8] = 0x0a;
        cmd[9] = 0x01;
        cmd[61] = 0x55;
        sendCMD(cmd);
    }

    /**
     * 设置obox的服务器连接状态
     *
     * @param isAdd  添加还是删除
     * @param ipByte 服务器地址
     */
    public void makeOboxCloudState(boolean isAdd, byte[] ipByte) {
        byte[] cmd = new byte[62];
        cmd[4] = (byte) 0x80;
        cmd[5] = 0x12;
        if (isAdd) {
            cmd[7] = 0x01;
            cmd[8] = 0x20;
            System.arraycopy(ipByte, 0, cmd, 9, ipByte.length);
        }
        cmd[61] = 0x55;
        sendCMD(cmd);
    }


    /**
     * 开始或者停止扫描节点
     *
     * @param mode        开启扫描，关闭扫描，通知所有节点可以重新入网
     * @param startId     开始id位置
     * @param time        扫描时间 30s-60s
     * @param context     上下文
     * @param showOnError 扫描指令出错显示
     */
    public void rfCmd(int mode, byte[] startId, int time, Context context, String showOnError) {
        byte[] cmd = new byte[62];
        cmd[index[6]] = 0x03;
        cmd[index[8]] = (byte) mode;
        if (startId != null) {
            if (startId.length > 3) {
                Toast.makeText(context, showOnError, Toast.LENGTH_SHORT).show();
            }
            System.arraycopy(startId, 0, cmd, index[9], startId.length);
        }
        cmd[index[12]] = time!=0?(byte) time:30;
        cmd[index[62]] = 0x55;
        sendCMD(cmd);
    }

    /**
     * 设定obox的时间
     *
     * @param time 建议传入系统当前时间
     */
    @SuppressWarnings("deprecation")
    public void setOboxTime(Time time) {
        time.setToNow();
        byte[] cmd = new byte[62];
        cmd[4] = (byte) 0x80;
        cmd[5] = 0x0d;
        cmd[7] = 0x08;//时区
        cmd[8] = (byte) (time.year - 2000);
        cmd[9] = (byte) (time.month + 1);
        cmd[10] = (byte) time.monthDay;
        if (time.weekDay == 7) {
            cmd[11] = 1;
        } else {
            cmd[11] = (byte) (time.weekDay + 1);
        }
        cmd[12] = (byte) time.hour;
        cmd[13] = (byte) time.minute;
        cmd[14] = (byte) time.second;
        cmd[61] = 0x55;
        sendCMD(cmd);
    }


    /**
     * 获取obox的情景信息
     *
     * @param factorsOfScene 获取情景的因素 ，ObScene.OBSCENE_ID,ObScene.OBSCENE_CONDITION:ObScene.OBSCENE_ACTION:
     * @param serNum         情景序号,或者编号，取id的时候传编号，取其他信息的时候传序号
     */
    public void reqScene(int factorsOfScene, int serNum, int num) {
        byte[] cmd = new byte[62];
        cmd[index[6]] = 0x0e;
        cmd[index[8]] = (byte) factorsOfScene;
        cmd[index[9]] = (byte) (factorsOfScene == ObScene.OBSCENE_ID ? 0 : serNum);
        cmd[index[10]] = (byte) num;
        cmd[61] = 0x55;
        sendCMD(cmd);
    }

    /**
     * 设置任何设备的状态，主要的状态设置在设置页面中设定
     *
     * @param obNode  obnode设备
     * @param isGroup 是否为组
     */
    public void setNodeState(ObNode obNode, boolean isGroup) {
        byte[] cmd = new byte[62];
        cmd[index[5]] = (byte) 0x81;
        System.arraycopy(obNode.getCplAddr(), 0, cmd, index[8], obNode.getCplAddr().length);
        if (isGroup) {
            cmd[index[8 + 6]] = (byte) 0xff;
        }
        System.arraycopy(obNode.getState(), 0, cmd, index[15], obNode.getState().length);
        cmd[61] = 0x55;
        sendCMD(cmd);
    }


}
