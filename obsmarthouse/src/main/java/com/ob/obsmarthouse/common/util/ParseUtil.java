package com.ob.obsmarthouse.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.widget.EditText;
import android.widget.Toast;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.bean.cloudbean.Obox;
import com.ob.obsmarthouse.common.bean.localbean.AirClean;
import com.ob.obsmarthouse.common.bean.localbean.Aircon;
import com.ob.obsmarthouse.common.bean.localbean.Als;
import com.ob.obsmarthouse.common.bean.localbean.Ammeter;
import com.ob.obsmarthouse.common.bean.localbean.Body;
import com.ob.obsmarthouse.common.bean.localbean.Co;
import com.ob.obsmarthouse.common.bean.localbean.Cooker;
import com.ob.obsmarthouse.common.bean.localbean.Environmental;
import com.ob.obsmarthouse.common.bean.localbean.Fan;
import com.ob.obsmarthouse.common.bean.localbean.Flood;
import com.ob.obsmarthouse.common.bean.localbean.Handset;
import com.ob.obsmarthouse.common.bean.localbean.Humidifier;
import com.ob.obsmarthouse.common.bean.localbean.Lamp;
import com.ob.obsmarthouse.common.bean.localbean.ObGroup;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.bean.localbean.ObScene;
import com.ob.obsmarthouse.common.bean.localbean.ObSensor;
import com.ob.obsmarthouse.common.bean.localbean.Obsocket;
import com.ob.obsmarthouse.common.bean.localbean.Pm;
import com.ob.obsmarthouse.common.bean.localbean.PowerCheck;
import com.ob.obsmarthouse.common.bean.localbean.Radar;
import com.ob.obsmarthouse.common.bean.localbean.SceneAction;
import com.ob.obsmarthouse.common.bean.localbean.SceneCondition;
import com.ob.obsmarthouse.common.bean.localbean.TheCurtain;
import com.ob.obsmarthouse.common.bean.localbean.Timing;
import com.ob.obsmarthouse.common.bean.localbean.Tv;
import com.ob.obsmarthouse.common.bean.localbean.WinCurtain;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.net.localnet.TcpSend;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 解析类
 * Created by adolf_dong on 2016/5/19.
 */
public class ParseUtil {


    private static byte[] getBytes(Message msg) {
        return msg.getData().getByteArray(OBConstant.StringKey.KEY);
    }

    public static int[] index = new int[64];

    static {
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }
    }


    /**
     * 获取oboxID
     *
     * @param msg                 msg
     * @param mySharedPreferences sharep数据
     * @param tcpSend             网络发送端
     * @param oboxSSID            保存当前连接名字的列表
     * @param tcpSends            网络发送的映射集合
     * @param context             环境
     * @param wrongPwd            是否在网络错误时刻
     * @return oboxID
     */
    public static String getOboxId(Message msg, SharedPreferences mySharedPreferences,
                                   TcpSend tcpSend, List<String> oboxSSID,
                                   Map<String, TcpSend> tcpSends,
                                   Context context, boolean wrongPwd) {
        byte[] bytes = getBytes(msg);
        return getWifiName(mySharedPreferences,
                bytes, tcpSend, oboxSSID,
                tcpSends,
                context, wrongPwd);
    }

    private static String getWifiName(SharedPreferences mySharedPreferences,
                                      byte[] data, TcpSend tcpSend, List<String> oboxSSID,
                                      Map<String, TcpSend> tcpSends,
                                      Context context, boolean wrongPwd) {

        int encryptionType = data[8] == 0 ? 0 : 1;
        byte[] temp = new byte[9];
        System.arraycopy(data, 9, temp, 0, 8);
        int i = 8;
        for (int j = 0; j < temp.length; j++) {
            if ((temp[j] & 0xff) == 0xff) {
                i = j;
                break;
            }
        }
        temp[8] = '\0';
        String ID;
        String id = null;
        try {
            ID = new String(temp, "utf-8");
            id = ID.substring(0, i);
            boolean oboxNameExist = false;
            for (String oboxName : oboxSSID) {
                if (oboxName.equalsIgnoreCase(id)) {
                    oboxNameExist = true;
                    break;
                }
            }
            if (!oboxNameExist) {
                oboxSSID.add(id);
                tcpSend.setOboxName(id);
                tcpSend.setEncryptionType(encryptionType);
                tcpSend.setPSW(mySharedPreferences.getString(id, OBConstant.StringKey.PSW));
                tcpSends.put(id, tcpSend);
            }
            /*ap模式并且没有被激活 */
            if ((encryptionType == 0) && NetState.isAp) {
                activateObox(tcpSend, context, mySharedPreferences, wrongPwd);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * @param tcpSend             连接发送端
     * @param context             环境
     * @param mySharedPreferences 存储
     * @param wrongPwd            是否密码错误情况
     */
    public static void activateObox(final TcpSend tcpSend, final Context context, final SharedPreferences mySharedPreferences, boolean wrongPwd) {
        final EditText et = new EditText(context);
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        et.setKeyListener(new NumberKeyListener() {
            protected char[] getAcceptedChars() {
                return Transformation.setType();
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_NUMBER;
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.login_title);
        if (wrongPwd) {
            builder.setMessage(context.getResources().getString(R.string.input) + tcpSend.getOboxName()
                    + context.getResources().getString(R.string.correct_pwd));
        } else {
            builder.setMessage(context.getResources().getString(R.string.activateObox_tips));
        }
        builder.setView(et);
        builder.setPositiveButton(R.string.sure, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String psw = et.getText().toString();
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (psw.length() == 8) {
                    tcpSend.changeOboxRfPsw(tcpSend.getPsw(), psw.getBytes());
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putString(tcpSend.getOboxName(), psw);
                    editor.apply();
                } else {
                    Toast.makeText(context, R.string.input_8_tips, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }




    /**
     * 解析单节点并决定流程终止或继续
     *
     * @param msg     msg
     * @param obNodes 节点装载容器
     * @param tcpSend 当前对应连接
     * @return 取单节点流程技术返回true，否则false,可根据返回值确定之后执行流程
     */
    public static boolean parseDevice(Message msg, List<ObNode> obNodes, TcpSend tcpSend) {
        byte[] bytes = getBytes(msg);
        byte num = bytes[9];
        byte[] rfaddr = Arrays.copyOfRange(bytes, 10, 15);
        byte addr = bytes[15];
        if (addr == 0) {
            return true;
        }
        byte[] id = Arrays.copyOfRange(bytes, 16, 32);
        byte[] serNum = Arrays.copyOfRange(bytes, 32, 37);
        byte parentType = bytes[37];
        byte type = bytes[38];
        byte[] version = Arrays.copyOfRange(bytes, 39, 47);
        byte surplusSence = bytes[47];
        byte groupAddr = 0;
        /*首次实例化对象没有组新信息和状态信息*/
        ObNode obNode = new ObNode(num, rfaddr, addr, id,
                serNum, parentType, type,
                version, surplusSence, groupAddr, null);
        addNodeWithType(obNodes, obNode);
        int index = MathUtil.validByte(num);
        if (index != 0xff) {
            tcpSend.getDevice(++index, false);
            return false;
        }
        return true;
    }

    /**
     * 根据不同类型做不同添加
     */
    private static void addNodeWithType(List<ObNode> obNodes, ObNode obNode) {
        switch (MathUtil.validByte(obNode.getParentType())) {
            case OBConstant.NodeType.IS_LAMP:
                Lamp lam = (Lamp) obNode;
                // FIXME: 2016/7/21 不能强转就new
//                Lamp lam = new Lamp()
                obNodes.add(lam);
                break;

            case OBConstant.NodeType.IS_COOKER:
                Cooker cooker = (Cooker) obNode;
                obNodes.add(cooker);
                break;

            case OBConstant.NodeType.IS_HUMIDIFIER:
                Humidifier hum = (Humidifier) obNode;
                obNodes.add(hum);
                break;
            case OBConstant.NodeType.IS_OBSOCKET:
                Obsocket obsocket = (Obsocket) obNode;
                obNodes.add(obsocket);
                break;
            case OBConstant.NodeType.IS_CURTAIN:
               onParseCurtain(obNodes, obNode);
                break;
            case OBConstant.NodeType.IS_FAN:
                Fan fan = (Fan) obNode;
                obNodes.add(fan);
                break;
            case OBConstant.NodeType.IS_AIR_CLEAN:
                AirClean airClean = (AirClean) obNode;
                obNodes.add(airClean);
                break;
            case OBConstant.NodeType.IS_TV:
                Tv tv = (Tv) obNode;
                obNodes.add(tv);
                break;

            case OBConstant.NodeType.AIR_CON:
                Aircon aircon = (Aircon) obNode;
                obNodes.add(aircon);
                break;
            case OBConstant.NodeType.IS_SENSOR:
                onParseSensor(obNodes, obNode);
                break;
            case OBConstant.NodeType.AMMETER:
                Ammeter am = (Ammeter) obNode;
                obNodes.add(am);
                break;
        }
    }

    /**解析幕布
     */
    private static void onParseCurtain(List<ObNode> obNodes, ObNode obNode) {
        switch (obNode.getType()) {
            case OBConstant.NodeType.WINDOW_CURTAINS:
                WinCurtain winCurtain = (WinCurtain) obNode;
                obNodes.add(winCurtain);
                break;
            case OBConstant.NodeType.THE_CURTAINS:
                TheCurtain theCurtain = (TheCurtain) obNode;
                obNodes.add(theCurtain);
                break;
        }
    }


    /**
     * 此方法只处理收到新节点的情况，其他2003返回在msg.what处理
     * 设备类型，设备子类型，设备id，序列号， 完整地址
     *
     * @param ObNodes 节点列表
     */
    public static void parseNewNode(Message msg, List<ObNode> ObNodes) {
        byte[] bytes = getBytes(msg);
        byte parentType = (byte) MathUtil.byteIndexValid(bytes[index[9]],0,7);
        byte type = bytes[index[10]];
        byte[] id = Arrays.copyOfRange(bytes, index[11], index[11] + 16);
        byte[] sernums = Arrays.copyOfRange(bytes, index[11] + 16, index[11] + 16 + 5);
        byte[] rfAddr = Arrays.copyOfRange(bytes, index[11] + 16 + 5, index[11] + 16 + 5 + 5);
        byte groupAddr = bytes[index[11] + 16 + 5 + 5];
        byte addr = bytes[index[11] + 16 + 5 + 5 + 1];
        ObNode newObNode = new ObNode(parentType, type, id, sernums,
                rfAddr, groupAddr, addr);
        addNodeWithType(ObNodes, newObNode);
    }


    /**
     * 当解析的参数是传感器
     */
    private static void onParseSensor(List<ObNode> obNodes, ObNode obNode) {
        switch (obNode.getType()) {
            case OBConstant.NodeType.ALS:
                Als als = (Als) obNode;
                obNodes.add(als);
                break;
            case OBConstant.NodeType.FLOOD:
                Flood flood = (Flood) obNode;
                obNodes.add(flood);
                break;
            case OBConstant.NodeType.RADAR:
                Radar radar = (Radar) obNode;
                obNodes.add(radar);
                break;
            case OBConstant.NodeType.CO:
                Co co = (Co) obNode;
                obNodes.add(co);
                break;
            case OBConstant.NodeType.ENVIRONMENTAL:
                Environmental en = (Environmental) obNode;
                obNodes.add(en);
                break;
            case OBConstant.NodeType.BODY:
                Body body = (Body) obNode;
                obNodes.add(body);
                break;
            case OBConstant.NodeType.PM2_5:
                Pm pm = (Pm) obNode;
                obNodes.add(pm);
                break;
            case OBConstant.NodeType.POWER_CHECK:
                PowerCheck pc = (PowerCheck) obNode;
                obNodes.add(pc);
                break;
        }
    }

    /**
     * 获取组并返回取组节点是否结束
     *
     * @param msg          msg
     * @param groupDevices 组装载容器
     * @param tcpSend      当前连接
     * @return 取组结束返回true
     */
    public static boolean parseGroup(Message msg, List<ObGroup> groupDevices, TcpSend tcpSend) {
        byte[] bytes = getBytes(msg);
        byte num = bytes[index[10]];
        byte addr = bytes[index[11]];
        if (addr == 0) {
            return true;
        }
        byte[] id = Arrays.copyOfRange(bytes, index[12], index[12] + 16);
        byte[] indexs = Arrays.copyOfRange(bytes, index[28], index[28] + 32);
        ObGroup obGroup = new ObGroup(num, addr, id, indexs);
        groupDevices.add(obGroup);
        int index = MathUtil.validByte(num);
        if (index != 0xff) {
            tcpSend.getDevice(++index, true);
            return false;
        }
        return true;
    }

    /**
     * 设置设备状态
     *
     * @param msg     msg
     * @param obNodes 待设置状态设备容器
     * @param tcpSend 当前连接
     * @return 流程结束返回true
     */
    public static boolean parseDeviceState(Message msg, List<ObNode> obNodes, TcpSend tcpSend) {
        int stateStart = 15;
        byte[] bytes = getBytes(msg);
        byte[] cplAddr = Arrays.copyOfRange(bytes, 7, 14);
        int len = obNodes.size();
        for (int i = 0; i < len; i++) {
            ObNode ld = obNodes.get(i);
            if (Arrays.equals(ld.getCplAddr(), cplAddr)) {
                byte[] state = null;
                switch (ld.getParentType()) {
                    case OBConstant.NodeType.IS_LAMP:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 7]);
                        break;
                    case OBConstant.NodeType.IS_COOKER:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 3]);
                        break;
                    case OBConstant.NodeType.IS_HUMIDIFIER:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 5]);
                        break;
                    case OBConstant.NodeType.IS_OBSOCKET:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 7]);
                        break;
                    case OBConstant.NodeType.IS_CURTAIN:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 2]);
                        break;
                    case OBConstant.NodeType.IS_FAN:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 6]);
                        break;
                    case OBConstant.NodeType.IS_AIR_CLEAN:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 5]);
                        break;
                    case OBConstant.NodeType.AIR_CON:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 5]);
                        break;
                    case OBConstant.NodeType.IS_SENSOR:
                        state = parseSensorState(stateStart, bytes, ld);
                        break;
                    case OBConstant.NodeType.AMMETER:
                        state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 8]);
                        break;
                }
                ld.setState(state);
                i++;
                if (i < obNodes.size()) {
                    tcpSend.getDeviceState(obNodes.get(i).getCplAddr(), null);
                }
                return false;
            }

        }
        return true;
    }

    /**
     * 返回当前传感器的状态
     *
     * @param stateStart 截取开始位置
     * @param bytes      传入字节数组
     * @param ld         当前节点
     * @return 对应的状态数组
     */
    private static byte[] parseSensorState(int stateStart, byte[] bytes, ObNode ld) {
        byte[] state = new byte[0];
        switch (ld.getType()) {
            case OBConstant.NodeType.ALS:
                state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 4]);
                break;
            case OBConstant.NodeType.FLOOD:
                state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 4]);
                break;
            case OBConstant.NodeType.RADAR:
                state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 4]);
                break;

            case OBConstant.NodeType.CO:
                state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 4]);
                break;
            case OBConstant.NodeType.ENVIRONMENTAL:
                state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 8]);
                break;
            case OBConstant.NodeType.BODY:
                state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 6]);
                break;
            case OBConstant.NodeType.PM2_5:
                state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 5]);
                break;
            case OBConstant.NodeType.POWER_CHECK:
                state = Arrays.copyOfRange(bytes, index[stateStart], index[stateStart + 5]);
                break;
        }
        return state;
    }


    /**
     * 解析obox,对obox各项参数进行设置,序列号，版本号
     *
     * @param msg     msg
     * @param obox    obox
     * @param tcpSend 当前选择的连接
     */
    public static void parseObox(Message msg, Obox obox, TcpSend tcpSend) {
        byte[] bytes = getBytes(msg);
        byte[] serNum = Arrays.copyOfRange(bytes, index[11], index[11] + 5);
        byte[] version = Arrays.copyOfRange(bytes, index[16], index[16] + 8);
        obox.setObox_pwd(tcpSend.getPSW());
        obox.setObox_name(tcpSend.getOboxName());
        obox.setObox_serial_id(serNum);
        obox.setObox_version(version);
    }


    /**
     * 200e
     * 解析和情景相关的返回,当编号是1，则解析情景id，编号2则解析情景条件  3则解析情景绑定的行为
     * 每当一个流程结束后，要么替换当前连接继续找寻情景，要么执行到此结束
     *
     * @param msg      传入消息
     * @param obScenes 本地情景列表
     * @param tcpSend  当前连接
     * @return 结束返回true
     */
    public static boolean parseScene(Message msg, List<ObScene> obScenes, List<ObNode> nodes, List<ObGroup> obGroups, TcpSend tcpSend) {
        byte[] data = getBytes(msg);
        int type = data[index[8]];
        switch (type) {
            case ObScene.OBSCENE_ID:
                return parseSceneId(obScenes, tcpSend, data);
            case ObScene.OBSCENE_CONDITION:
                return parseSceneCondition(obScenes, nodes, tcpSend, data);
            case ObScene.OBSCENE_ACTION:
                return parseSceneAction(obScenes, nodes, obGroups, tcpSend, data);
        }
        return false;
    }

    /**
     * 解析情景内的行为节点
     *
     * @return 当结束后也即当前obox情景部分信息收集完毕
     */
    private static boolean parseSceneAction(List<ObScene> obScenes, List<ObNode> nodes, List<ObGroup> obGroups, TcpSend tcpSend, byte[] data) {
        int sceneSernum = MathUtil.validByte(data[index[10]]);
                /*此处num只是帧数标志，每个帧，只要此位置不为0xff则其内必然包含三个行为节点，
                否则，若此位置为0xff则表示情景内部节点读取结束，需判断帧内具体的行为节点数量*/
        ObScene obScene = getObSceneforSer(obScenes, sceneSernum);
                    /*通过完整地址查找对应节点添加到数据*/
        for (int i = 0; i < 3; i++) {
            byte[] cplAddr = Arrays.copyOfRange(data, index[12 + 15 * i], index[19 + 15 * i]);
            if (cplAddr[index[7]] == 0) {
                break;
            }
            byte[] action = Arrays.copyOfRange(data, index[19 + 15 * i], index[27 + 15 * i]);
            /*子节点地址为0xff则是组*/
            boolean isGroup = MathUtil.validByte(cplAddr[index[7]]) == 0xff;
            initAction(isGroup, obGroups, nodes, sceneSernum, obScene, cplAddr, action);
        }
        int num = MathUtil.validByte(data[index[11]]);
        if (num != 0xff) {
            tcpSend.reqScene(ObScene.OBSCENE_ACTION, sceneSernum, ++num);
        } else {
            int scenenum = obScene.getNum();
            /*是最后一个情景？*/
            if (scenenum == 0xff) {
                return true;
            } else {
                tcpSend.reqScene(ObScene.OBSCENE_ID, 0, ++scenenum);
            }
        }
        return false;
    }

    /**
     * 根据完整地址实例化情景内节点
     */
    private static void initAction(boolean isgourp, List<ObGroup> obGroups, List<ObNode> obNodes,
                                   int sceneSernum, ObScene obScene, byte[] cplAddr, byte[] action) {
        for (int nodeIndex = 0; nodeIndex < (isgourp ? obGroups.size() : obNodes.size()); nodeIndex++) {
            SceneAction obNode;
            if (isgourp) {
                obNode = obGroups.get(nodeIndex);
            } else {
                obNode = obNodes.get(nodeIndex);
            }
            if (Arrays.equals(obNode.getAddrs(), cplAddr)) {
                obNode.putAction(sceneSernum, action);
                obScene.getObNodes().add(obNode);
                break;
            }
        }
    }

    /**
     * 解析情景条件
     */
    private static boolean parseSceneCondition(List<ObScene> obScenes, List<ObNode> nodes, TcpSend tcpSend, byte[] data) {
        int sernum = MathUtil.validByte(data[index[10]]);
        ObScene obScene = getObSceneforSer(obScenes, sernum);
        List<SceneCondition> sceneConditions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int conditionType = MathUtil.byteIndexValid(data[index[12]], 2 * i, 2);
            byte[] conditionaddr = MathUtil.validArray(Arrays.copyOfRange(data, index[13 + i * 15], index[20 + i * 15]));
            final byte[] condition = MathUtil.validArray(Arrays.copyOfRange(data, index[20 + i * 15], index[28 + i * 15]));
            SceneCondition sceneCondition = null;

            switch (conditionType) {
                case SceneCondition.SIMPLE:
                    // FIXME: 2016/7/6 如果所有的情景条件都是simple表示此情景为一般情景
                    continue;
                case SceneCondition.TIMING:
                    sceneCondition = new Timing(condition);
                    break;
                case SceneCondition.SENSOR:
                    for (int index = 0; index < nodes.size(); index++) {
                        ObNode obNode = nodes.get(index);
                        if (Arrays.equals(conditionaddr, obNode.getCplAddr())) {
                            sceneCondition = (ObSensor) obNode;
                            break;
                        }
                    }
                    break;
                case SceneCondition.CONTROL:
                    // FIXME: 2016/7/1 暂不实现
                    sceneCondition = new Handset();
                    break;
                default:
                    break;
            }
            if (obScene == null) {
                return true;
            }
            // FIXME: 2016/7/6 此处暂时先把所有的情景内条件统一管理
            sceneConditions.add(sceneCondition);
        }
        obScene.getSceneCondition().add(sceneConditions);
        int num = MathUtil.validByte(data[index[10]]);
        if (num != 0xff) {
            tcpSend.reqScene(ObScene.OBSCENE_CONDITION, obScene.getSerisNum(), ++num);
        } else {
            tcpSend.reqScene(ObScene.OBSCENE_ACTION, obScene.getSerisNum(), 1);
        }
        return false;
    }

    /**
     * 根据序列号返回目标情景
     *
     * @param obScenes 情景列表
     * @param sernum   序列号
     * @return 目标情景
     */
    private static ObScene getObSceneforSer(List<ObScene> obScenes, int sernum) {
        ObScene obScene = null;
        for (int i = 0; i < obScenes.size(); i++) {
            obScene = obScenes.get(i);
            if (obScene.getSerisNum() == sernum) {
                break;
            } else {
                obScene = null;
            }
        }
        return obScene;
    }

    /**
     * 解析情景id
     */
    private static boolean parseSceneId(List<ObScene> obScenes, TcpSend tcpSend, byte[] data) {
        int serNum = MathUtil.validByte(data[index[10]]);
        if (serNum == 0) {
            /*不存在情景*/
            return true;
        }
        boolean isEnable = MathUtil.byteIndexValid(data[index[9]], 4) == 1;
        byte[] id = new byte[OBConstant.NodeType.ID_LEN];
        System.arraycopy(data, index[12], id, 0, id.length);
        int number = MathUtil.validByte(data[index[11]]);
        ObScene obScene = new ObScene(serNum, id, isEnable, number);
        obScenes.add(obScene);
        tcpSend.reqScene(ObScene.OBSCENE_CONDITION, serNum, 1);
        return false;
    }


}
