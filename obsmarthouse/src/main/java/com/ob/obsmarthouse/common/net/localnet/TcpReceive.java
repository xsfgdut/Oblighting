package com.ob.obsmarthouse.common.net.localnet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ob.obsmarthouse.common.util.MathUtil;
import com.ob.obsmarthouse.common.util.NetLock;
import com.ob.obsmarthouse.common.util.NetState;
import com.ob.obsmarthouse.common.util.Sbox;
import com.ob.obsmarthouse.common.util.Transformation;
import com.ob.obsmarthouse.common.constant.OBConstant;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class TcpReceive extends Thread {
    public static int[] index = new int[64];

    static {
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }
    }

    private Socket mSocket;
    private Handler mHandler;
    private static final String TAG = "Recive";
    private boolean canRec = true;
    private byte[] mPswbytes;
    private ByteBuffer validBuffer = ByteBuffer.allocate(512);

    public TcpReceive(Socket mSocket, Handler mHandler) {
        this.mSocket = mSocket;
        this.mHandler = mHandler;
    }


    @Override
    public void run() {
        try {
            while (canRec) {

                int len = 68;
                byte[] sData = new byte[len];
                int rlRead = mSocket.getInputStream().read(sData);
                if (rlRead == 68) {
                    byte[] receiveData = new byte[rlRead];
                    System.arraycopy(sData, 0, receiveData, 0, rlRead);
                    getValidData(receiveData, validBuffer);
                    Arrays.fill(sData, (byte) 0);
                    byte[] goal = Sbox.unPack(receiveData, mPswbytes);
                    Log.i(TAG, "recive = >>" + Transformation.byteArryToHexString(goal));
                    onRecieve(goal);
                    validBuffer.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getValidData(byte[] src, ByteBuffer buf) {
        boolean isValid = true;
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            if (i == 0 && v == 255) {
                isValid = false;
            }
            if (isValid) {
                if (buf.position() < 32) {
                    buf.put(src[i]);
                }
            } else {
                if (v != 255) {
                    isValid = true;
                    if (buf.position() < 32) {
                        buf.put(src[i]);
                    }
                }
            }
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * cmd是45字节，有效数据从第7字节开始
     * 前面4个字节的0，两个字节的cmd，1个字节的长度，接payload
     *
     * @param bf 转换后的64字节数据
     */
    private void onRecieve(byte[] bf) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putByteArray(OBConstant.StringKey.KEY, bf);
        msg.setData(bundle);
        if (!NetLock.isCompile(bf)) {
            return;
        }
        if (NetState.isTranse || NetState.isBurn) {
            onUpdate(bf[0], msg);
            return;
        }
        int cmd = ((bf[4] & 0xff) << 8) + (bf[5] & 0xff);
        switch (cmd) {
            case 0x2001:
                msg.what = OBConstant.ReplyType.GET_OBOX_NAME_BACK;
                break;
            case 0xa007:
                onChangPwd(bf[7], msg);
                break;
            /*获取obox信息，obox内节点信息，组信息，版本信息*/
            case 0xa013:
                onGetMsg(bf, msg);
                break;
            /*获取状态返回*/
            case 0x2100:
                onGetState(msg);
                break;
             /* 设置连接与断开服务器*/
            case 0xa012:
                onSetOboxMode(bf, msg);
                break;
            /*获取情景信息*/
            case 0x200e:
                onReceiveScene(msg);
                break;
            case 0x2003:
                onRfCmd(bf, msg);
                break;
            default:
                break;
        }
        mHandler.sendMessage(msg);
    }

    private void onRfCmd(byte[] bf, Message msg) {
        byte isSuc = bf[index[8]];
        byte setType = bf[index[9]];
        if (MathUtil.validArray(bf).length > 20) {
            msg.what = OBConstant.ReplyType.SEARCH_NODE_SUC;
        } else {
            switch (setType) {
                case 0:
                    msg.what = isSuc == 1 ? OBConstant.ReplyType.STOP_SEARCH_SUC : OBConstant.ReplyType.SEARCH_NODE_FAL;
                    break;
                case 1:
                    msg.what = isSuc == 1 ? OBConstant.ReplyType.SEARCH_SUC : OBConstant.ReplyType.SEARCH_FAL;
                    break;
                case 2:
                    msg.what = isSuc == 1 ? OBConstant.ReplyType.FORCE_SEARCH_SUC : OBConstant.ReplyType.FORCE_SEARCH_FAL;
                    break;
            }
        }
    }

    /**
     * 收到情景相关回复
     */
    private void onReceiveScene(Message msg) {
        msg.what = OBConstant.ReplyType.GET_SCENE_BACK;
    }

    /**
     * 升级情况是特殊情况特殊处理
     */
    private void onUpdate(byte src, Message msg) {
        int type = MathUtil.validByte(src);
        switch (type) {
            case 0xb1:
                msg.what = OBConstant.ReplyType.BURN_BACK;
                break;
            case 0xb2:
                msg.what = OBConstant.ReplyType.UP_BACK;
                break;
            case 0xb3:
                msg.what = OBConstant.ReplyType.WIPE_BACK;
                break;
            case 0xb4:
                msg.what = OBConstant.ReplyType.PROTECT_BACK;
                break;
            default:
                break;
        }
        mHandler.sendMessage(msg);
    }


    private void onGetState(Message msg) {
        msg.what = OBConstant.ReplyType.GET_STATE;
    }

    /**
     * 获得obox内的版本信息
     */
    private void onGetMsg(byte[] bf, Message msg) {
        switch (bf[7]) {
            case 3:
                switch (bf[8]) {
                    /*节点信息 */
                    case 2:
                        msg.what = OBConstant.ReplyType.GET_SINGLENODE_BACK;
                        break;
                    /*组信息 */
                    case 4:
                        msg.what = OBConstant.ReplyType.GET_GROUP_BACK;
                        break;
                    /*obox信息  序列号 版本号 */

                    case 10:
                        msg.what = OBConstant.ReplyType.GET_OBOX_MSG_BACK;
                        break;
                    case 11:
                        msg.what = OBConstant.ReplyType.ON_SET_MODE;
                        break;
                }
        }
    }

    /**
     * 设置obox连接模式，设置obox
     */
    private void onSetOboxMode(byte[] bf, Message msg) {
        switch (bf[7]) {
            /*操作失败*/
            case OBConstant.ReplyType.FAL:
                switch (bf[8]) {
                    case 0:
                        msg.what = OBConstant.ReplyType.CLOSE_CLOUD_FAL;
                        break;
                    case 1:
                        msg.what = OBConstant.ReplyType.OPEN_CLOUD_FAL;
                        break;
                }
                break;
            case OBConstant.ReplyType.SUC:
                switch (bf[8]) {
                    /*关闭服务器连接*/
                    case 0:
                        msg.what = OBConstant.ReplyType.CLOSE_CLOUD_SUC;
                        break;
                    /*打开服务器连接*/
                    case 1:
                        msg.what = OBConstant.ReplyType.OPEN_CLOUD_SUC;
                        break;
                }
                break;
        }
    }

    /**
     * 修改obox的密码
     */
    private void onChangPwd(byte b, Message msg) {
        switch (b) {
            case OBConstant.ReplyType.FAL:
                msg.what = OBConstant.ReplyType.CHANG_RF_PSW_FAL;
                break;
            case OBConstant.ReplyType.SUC:
                msg.what = OBConstant.ReplyType.CHANG_RF_PSW_SUC;
                break;
        }
    }

    /**
     * 断开连接
     */
    public void disConnect() {
        canRec = false;
    }

    /**
     * 以字节数组方式设置解析密码
     */
    public void setPswbytes(byte[] pswbytes) {
        mPswbytes = pswbytes;
    }


}
