package com.ob.obsmarthouse.common.util;


import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.bean.cloudbean.Obox;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.bean.localbean.ObGroup;
import com.ob.obsmarthouse.common.bean.localbean.Version;
import com.ob.obsmarthouse.common.constant.OBConstant;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * 算法工具
 * Created by adolf_dong on 2016/5/23.
 */
public class MathUtil {
    /**
     * 获得数组中的有效数据
     *
     * @param arry 源数组
     * @return 去掉无用位置的有效数组
     */
    public static byte[] validArray(byte[] arry) {
        String cache = null;
        try {
            cache = new String(arry, OBConstant.StringKey.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] goal = null;
        try {
            if (cache != null) {
                goal = cache.trim().getBytes(OBConstant.StringKey.UTF8);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return goal;
    }

    /**
     * 获取字节数据的去符号位值
     */
    public static int validByte(byte src) {
        if (src < 0) {
            return src & 0xff;
        }
        return src;
    }

    /**
     * 获取字节中的特定一个位值
     *
     * @param src   byte
     * @param index 0-7 取第几个位
     * @return 第index位的值  0 1
     */
    public static int byteIndexValid(byte src, int index) {
        return ((src & 0xff) >> index) & 0x01;
    }

    /**
     * 从直接的startPos开始取len长度的bit的值
     *
     * @param src      字节
     * @param startPos 开始位置，注意bit7-bit0的bit顺序
     * @param len      取的长度
     */
    public static int byteIndexValid(byte src, int startPos, int len) {
        if (startPos + len > 7) {
            throw new IndexOutOfBoundsException();
        }
        int srcInt = src & 0xff;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += ((srcInt >> (startPos + i)) & 0x01) << i;
        }
        return sum;
    }

    /**
     * 通过组索引获得组成员
     *
     * @param bytes    组索引
     * @param integers 组成员装载容器
     */
    public static void index2List(byte[] bytes, List<Integer> integers) {
        for (int index = 0; index < 32; index++) {
            if (bytes[index] == 0) {
                continue;
            }
            for (int i = 0; i < 8; i++) {
                if (((bytes[index] >> i) & 0x01) != 0) {
                    int indexVal = index * 8 + i;
                    integers.add(indexVal);
                }
            }
        }
    }

    /**判断字节数组值是否为0
     */
    public static boolean byteArrayIsZero(byte[] src) {
        for (byte aSrc : src) {
            if (aSrc != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单节点寻找组地址
     *
     * @param obGroups   组列表
     * @param obNodes    单节点列表
     * @param wantRemove 是否需要从单节点数据中删除，并添加到obGroup中,建议为false
     */
    public static void nodeFindGroup(List<ObGroup> obGroups, List<ObNode> obNodes, boolean wantRemove) {
        for (int i = 0; i < obGroups.size(); i++) {
            ObGroup obGroup = obGroups.get(i);
            List<Integer> integers = obGroup.getIndexList();
            for (int j = 0; j < obNodes.size(); j++) {
                ObNode obNode = obNodes.get(j);
                obGroup.setRfAddrs(obNode.getRfAddr());
                int addr = validByte(obNode.getAddr());
                if (integers.contains(addr)) {
                    obNode.setGroupAddr(obGroup.getAddr());
                    if (wantRemove) {
                        obGroup.getObNodes().add(obNode);
                        obNodes.remove(obNode);
                    }
                }
            }
        }

    }

    /**
     * 生成用于展现的数据
     *
     * @param srcObNodes   单节点数据源，建议通过datapool的getNodesforType得到值
     * @param srcObGroups  组数据源
     * @param showObGroups 目标展现组节点数据，进入设置页面立即实例化
     */
    public static void makeShowData(List<ObNode> srcObNodes, List<ObGroup> srcObGroups, List<ObGroup> showObGroups) {
        for (int j = 0; j < srcObGroups.size(); j++) {
            ObGroup obGroup = srcObGroups.get(j);
            for (int i = 0; i < srcObNodes.size(); i++) {
                ObNode obNode = srcObNodes.get(i);
                if (obNode.getGroupAddr() == obGroup.getAddr()) {
                    obGroup.getObNodes().add(obNode);
                    srcObNodes.remove(obNode);
                    i--;
                }
            }
            /*对于内部无节点的可以显示，利于对组进行操作以防止obox资源被空组闲置*/
            if (obGroup.getObNodes().size() != 0 || byteArrayIsZero(obGroup.getIndexs())) {
                showObGroups.add(obGroup);
            }
        }
    }


    /**
     * 根据本地数据转成用于上传服务器的数据
     *
     * @param dvcs     用于服务器的数据容器
     * @param localDvs 本地数据容器
     */
    public static void mackCloudData(List<DeviceConfig> dvcs, List<ObNode> localDvs, Obox obox) {
        for (ObNode ld : localDvs) {
            DeviceConfig dc = new DeviceConfig(ld.getId(), ld.getSerNum(), ld.getAddr(), ld.getGroupAddr(),
                    ld.getState(), ld.getParentType(), ld.getType(), ld.getVersion());
            dc.setObox_serial_id(obox.getObox_serial_id());
            dvcs.add(dc);
        }
    }

    /**
     * 判断是否能升级
     *
     * @param fileVersion 本地升级文件中获取的版本类
     * @param netVersion  网络获取的版本类
     * @return 能升级则为true，否则为false
     */
    public static boolean canUpdate(Version fileVersion, Version netVersion) {
        boolean canUpdate = false;
        if (Arrays.equals(fileVersion.getHardVersion(), netVersion.getHardVersion())) {
            if (fileVersion.getMainVersion() > netVersion.getMainVersion()) {
                canUpdate = true;
            } else if (fileVersion.getMainVersion() == netVersion.getMainVersion()) {
                if (fileVersion.getVersion() > netVersion.getVersion()) {
                    canUpdate = true;
                }
            }
        }
        return canUpdate;
    }


}
