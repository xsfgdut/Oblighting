package com.ob.obsmarthouse.common.net.cloudnet;


import android.util.Log;

import com.google.gson.Gson;
import com.ob.obsmarthouse.common.bean.User;
import com.ob.obsmarthouse.common.bean.cloudbean.Action;
import com.ob.obsmarthouse.common.bean.cloudbean.CloudScene;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.bean.cloudbean.Obox;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.util.Transformation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GetParameter {
    /**
     * 执行动作
     */
    public static String ACTION;

    /**
     * 口令
     */
    public static String ACCESSTOKEN;
    public static int ACTION_TIME = 1;

    /**
     * 注册
     *
     * @param name 用户名
     * @param psw  密码
     */
    public static List<NameValuePair> onRegister(String name, String psw) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.REGISTER));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.USERNAME, name));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.PASS_WORD, psw));
        return nvps;

    }

    /**
     * 登录
     *
     * @param name 用户名
     * @param psw  密码
     */
    public static List<NameValuePair> onLogin(String name, String psw) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.LOGIN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.USERNAME, name));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.PASS_WORD, psw));
        return nvps;
    }

    /**
     * 上传之前的检测，如果返回true则存在以及绑定成功，如果返回false则不存在，可执行后续addobox（）
     *
     * @param obox 选中的obox
     */
    public static List<NameValuePair> onQueryOboxBind(Obox obox) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.QUERY_OBOX_BIND));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        return nvps;
    }

    /**
     * 添加obox
     *
     * @return 控制数据集
     */
    public static List<NameValuePair> onAddObox(Obox obox) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.ADD_OBOX));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        Gson gson = new Gson();
        String json = gson.toJson(obox);
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX, json));
        return nvps;

    }

    /**
     * 设置单节点或者组节点的状态
     *
     * @param deviceConfig 节点
     * @param isBili  是否闪烁
     * @return
     */
    public static List<NameValuePair> onSetNodeState(DeviceConfig deviceConfig,boolean isBili) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.SETTING_NODE_STATUS));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, deviceConfig.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.GROUPADDR,"00"));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ADDR, deviceConfig.getAddr()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.STATE,
                deviceConfig.getState().substring(0,12)+(isBili?CloudConstant.ParameterValue.BILI:CloudConstant.ParameterValue.LightDef)));
        return nvps;
    }

    private static final String TAG = "GetParameter";


    /**
     * 删除obox
     *
     * @param obox 要删除的obox  在每次点击操作之前替换引用指向的实例
     */
    public static List<NameValuePair> onDelObox(Obox obox) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.DELETE_OBOX));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.FORCE, CloudConstant.ParameterValue.FORCE_TRUE));
        return nvps;
    }

    /**
     * 组结构操作
     *
     * @param obox         当前操作的obox
     * @param isAdd        是添加还是删除
     * @param superId      组id
     * @param deviceConfig 当前操作的节点
     */
    public static List<NameValuePair> onOpreGoupMeb(Obox obox, boolean isAdd, String superId, DeviceConfig deviceConfig) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.OPERATE_GROUP_MEMBERS));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OPERATE_TYPE, isAdd ? CloudConstant.ParameterValue.IS_ADD_MEMBER : CloudConstant.ParameterValue.IS_DEL_MEMBER));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.SUPERID, superId));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.DEVICE_ID, deviceConfig.getName()));
        return nvps;
    }

    /**
     * @param obox         当前obox
     * @param deviceConfig 要删除的组中的节点，以此传入组id
     */
    public static List<NameValuePair> onDelGoup(Obox obox, DeviceConfig deviceConfig) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.OPERATE_GROUP_MEMBERS));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OPERATE_TYPE, CloudConstant.ParameterValue.IS_DEL_MEMBER));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.DEVICE_ID, null));
        return nvps;
    }


    /**
     * 删除单节点
     *
     * @param obox         选中obox
     * @param deviceConfig 选中节点
     */
    public static List<NameValuePair> onDelNode(Obox obox, DeviceConfig deviceConfig) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.DELETE_SINGLE_DEVICE));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.DEVICE_ID, deviceConfig.getName()));
        return nvps;
    }

    /**
     * 给节点进行重命名
     *
     * @param obox         当前操作的obox
     * @param deviceConfig 当前操作设备
     * @param newID        新ID
     * @param isGroup      是否为组操作
     */
    public static List<NameValuePair> onRenameNode(Obox obox, DeviceConfig deviceConfig, String newID, boolean isGroup) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.UPDATE_NODE_NAME));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.NODE_TYPE, isGroup ? CloudConstant.NodeType.IS_GROUP : CloudConstant.NodeType.IS_SINGLE));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.NEW_ID, newID));
        return nvps;
    }

    /**
     * 第一步
     * id与条件级别的情景操作
     *
     * @param obox   操作obox
     * @param action 动作， 包括 CREAT_SCENE DELETE_SCENE EXECUTE_SCENE RENAME_SCENE MODIFY_SCENE
     * @param cloudScene  新建或已经存在的scene
     */
    public static List<NameValuePair> onOpreaScene(Obox obox, String action, CloudScene cloudScene) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.SETTING_SC_ID));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OPERATE_TYPE, action));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.SCENE_NUMBER, cloudScene.getScene_number()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.SCENE_REMOTER_INDEX, cloudScene.getRemoter_index()));
        /*modify没用，修改情景的是否执行状态也是用rename*/
        if (action.equals(CloudConstant.ParameterValue.CREAT_SCENE) || action.equals(CloudConstant.ParameterValue.RENAME_SCENE)) {
            nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACTION_TYPE, cloudScene.getScene_status()));
            nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.SCENE_ID, cloudScene.getScene_id()));
        }

        return nvps;
    }


    /**
     * 第二步
     * 设置场景序号的条件信息
     *
     * @param obox   选中的obox
     * @param action 执行动作，添加  删除   修改
     * @param cloudScene  当前情景
     */
    public static List<NameValuePair> settingScCondition(Obox obox, String action, CloudScene cloudScene) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.SETTING_SC_CONDITION));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OPERATE_TYPE, action));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.SCENE_NUMBER, cloudScene.getScene_number()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.SCENE_TYPE, cloudScene.getScene_type()));
        //删除和改变为非传感器场景则不需要传condition相关参数,即只要是删除，或者情景模式不是传感器，都不传此参数
//        if (!(action.equals(CloudConstant.DEL_CONDITION)||!cloudScene.getScene_type().equals(CloudConstant.SENSOR_SCENE))) {
//            nvps.add(new BasicNameValuePair(CloudConstant.CONDITION_ID,cloudScene.getCondition_id()));
//            nvps.add(new BasicNameValuePair(CloudConstant.CONDITION,cloudScene.getCondition()));
//        }
        if (action.equals(CloudConstant.ParameterValue.CREAT_CONDITION) || action.equals(CloudConstant.ParameterValue.UPDATE_CONDITION)) {
            if (cloudScene.getScene_type().equals(CloudConstant.ParameterValue.TIM_SCENE)) {
                nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CONDITION, cloudScene.getCondition()));
            } else if (cloudScene.getScene_type().equals(CloudConstant.ParameterValue.SENSOR_SCENE)) {
                nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CONDITION, cloudScene.getCondition()));
                nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CONDITION_ID, cloudScene.getCondition_id()));
            }
        }
        return nvps;
    }

    /**
     * 第三步
     * 设置场景序号的行为节点
     *
     * @param obox    当前选中的obox
     * @param action  操作类型 添加 删除
     * @param cloudScene   情景
     * @param actions 情景行为节点
     */
    public static List<NameValuePair> onOpretaScAction(Obox obox, String action, CloudScene cloudScene, Action actions) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.SETTING_SC_ACTION));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.SCENE_NUMBER, cloudScene.getScene_number()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OPERATE_TYPE, action));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACTION_ID, actions.getAction_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACTION, actions.getAction() + "0000"));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.NODE_TYPE, actions.getNode_type()));
        return nvps;
    }

    /**
     * 查询obox中的所有情景   此方法查询后 ， 返回obox中情景的映射Map，要正常显示，则要进行转换到List处理
     *
     * @param obox 请求的obox
     */
    public static List<NameValuePair> onQueryScenes(Obox obox) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.QUERY_SCENES));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        return nvps;
    }

    /**
     * 改变obox的id
     *
     * @param obox     当前obox
     * @param oboxName obox新id
     */
    public static List<NameValuePair> onChangeOboxId(Obox obox, String oboxName) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.UPDATE_OBOX_NAME));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_NEW_NAME, oboxName));
        return nvps;
    }

    /**
     * 改变obox的控制密码
     *
     * @param obox    当前obox
     * @param oboxPsw obox新密码
     */
    public static List<NameValuePair> onChangeOboxPsw(Obox obox, String oboxPsw) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.UPDATE_OBOX_PASSWORD));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_OLD_PWD, obox.getObox_pwd()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_NEW_PWD, oboxPsw));
        return nvps;
    }

    /**
     * 重置obox的控制密码
     *
     * @param obox 当前obox
     */
    public static List<NameValuePair> onResetOboxPsw(Obox obox) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.RESET_OBOXPWD));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        return nvps;
    }

    /**
     * 查询所有升级信息
     */
    public static List<NameValuePair> onQueryUpgrades() {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.QUERY_UPGRADES));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        return nvps;
    }

    /**
     * 释放单个obox中的设备
     */
    public static List<NameValuePair> onReleaseDevice(Obox obox) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.RELEASE_ALL_DEVICES));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.OBOX_SERIAL_ID, obox.getObox_serial_id()));
        return nvps;
    }

    public static List<NameValuePair> onQueryDevice(User user,int index ,int count) {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.CMD, CloudConstant.CmdValue.QUERY_DEVICE));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ACCESS_TOKEN, ACCESSTOKEN));
        // FIXME: 2016/8/12 访客模式不传这些参数
//        if (user.getAdminName()!=null) {
//            nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.ADMIN_NAME, user.getAdminName()));
//        }
//
//        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.GUEST_NAME, user.getGuestName()));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.START_INDEX, String.valueOf(index)));
        nvps.add(new BasicNameValuePair(CloudConstant.ParameterKey.COUNT, String.valueOf(count)));
        return nvps;
    }
}
