package com.ob.obsmarthouse.common.util;

import com.ob.obsmarthouse.common.bean.cloudbean.CloudGroup;
import com.ob.obsmarthouse.common.bean.cloudbean.CloudScene;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.bean.localbean.ObGroup;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.bean.localbean.ObScene;
import com.ob.obsmarthouse.common.constant.OBConstant;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * Created by adolf_dong on 2016/6/16.
 */
public class StringUtil {
    /**
     * @param str   判断字符
     * @param isPwd 判断数据是否为密码
     * @param len   字符串转成utf-8字节数组的长度
     * @return 合法
     */
    public static boolean isLegit(String str, boolean isPwd, int len) {
//        String limitEx="^[a-zA-Z0-9\u4E00-\u9FA5]{0,5}|[a-zA-Z0-9]{0,16}$";
        // FIXME: 2016/6/12
        String limitEx;
        if (isPwd) {
            limitEx = "[A-z0-9]{4}";
        } else {
            limitEx = "[A-z0-9\u4e00-\u9fa5]{1,16}";
        }
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        boolean lenVial = false;
        try {
            lenVial = str.getBytes(OBConstant.StringKey.UTF8).length <= len;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return m.matches() && lenVial;
    }

    /**
     * 判断情景名是否存在
     *
     * @param newName  情景名
     * @param obScenes 本地情景列表 可为null
     * @param scenes   服务器情景列表 可为null
     * @param isLocal  是否本地
     * @return 存在返回true，不存在返回false
     */
    public static boolean sceneIsExit(String newName, List<ObScene> obScenes, List<CloudScene> scenes, boolean isLocal) {
        boolean isExit = false;
        if (isLocal) {
            if (obScenes == null) {
                return false;
            }
            for (int i = 0; i < obScenes.size(); i++) {
                ObScene cloudScene = obScenes.get(i);
                if (getUtf8(cloudScene.getSceneId()).equals(newName)) {
                    isExit = true;
                    break;
                }
            }

        } else {
            if (scenes == null) {
                return false;
            }
            for (int i = 0; i < scenes.size(); i++) {
                CloudScene cloudScene = scenes.get(i);
                if (newName.equals(cloudScene.getScene_id())) {
                    isExit = true;
                    break;
                }
            }
        }
        return isExit;
    }

    /**
     * 判断单节点名称是否已经存在
     *
     * @param nodeName      判断名称
     * @param nodes         本地节点列表
     * @param deviceConfigs 服务器节点列表
     * @param isLocal       是否本地
     * @return 存在则true
     */
    public static boolean nodeIsExit(String nodeName, List<ObNode> nodes, List<DeviceConfig> deviceConfigs, boolean isLocal) {
        boolean isExit = false;
        if (isLocal) {
            if (nodes == null) {
                return false;
            }
            for (int i = 0; i < nodes.size(); i++) {
                ObNode obNode = nodes.get(i);
                if (nodeName.equals(getUtf8(obNode.getId()))) {
                    isExit = true;
                    break;
                }
            }
        } else {
            if (deviceConfigs == null) {
                return false;
            }
            for (int i = 0; i < deviceConfigs.size(); i++) {
                DeviceConfig deviceConfig = deviceConfigs.get(i);
                if (nodeName.equals(deviceConfig.getName())) {
                    isExit = true;
                    break;
                }
            }
        }
        return isExit;
    }

    /**判断组是否存在
     * @param groupName 组名称
     * @param obGroups 本地组列表
     * @param cloudGroups 服务器版本组列表
     * @param isLocal 是否本地
     * @return 存在则true
     */
    public static boolean groupIsExit(String groupName, List<ObGroup> obGroups, List<CloudGroup> cloudGroups, boolean isLocal) {
        boolean isExit = false;
        if (isLocal) {
            if (obGroups == null) {
                return false;
            }
            for (int i = 0; i < obGroups.size(); i++) {
                ObGroup obGroup = obGroups.get(i);
                if (groupName.equals(getUtf8(obGroup.getId()))) {
                    isExit = true;
                    break;
                }
            }
        } else {
            if (cloudGroups == null) {
                return false;
            }
            for (int i = 0; i < cloudGroups.size(); i++) {
                CloudGroup obGroup = cloudGroups.get(i);
                if (groupName.equals(obGroup.getId())) {
                    isExit = true;
                    break;
                }
            }
        }
        return isExit;
    }

    /**获取utf-8编码String
     * @param src 字节数组
     */
    public static String getUtf8(byte[] src) {
        String goal = null;
        try {
            goal = new String(src, OBConstant.StringKey.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return goal;
    }
}
