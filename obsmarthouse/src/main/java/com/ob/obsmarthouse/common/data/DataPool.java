package com.ob.obsmarthouse.common.data;

import com.ob.obsmarthouse.common.bean.Position;
import com.ob.obsmarthouse.common.bean.cloudbean.Obox;
import com.ob.obsmarthouse.common.bean.localbean.ObGroup;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.bean.localbean.ObScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地连接装载数据的池
 * Created by adolf_dong on 2016/5/11.
 */
public class DataPool {

    public static List<Position> getPositions() {
        return positions;
    }

    public static void setPositions(List<Position> positions) {
        DataPool.positions = positions;
    }

    /**
     * position list along  user
     */
    private static List<Position> positions = new ArrayList<>();
    /**
     * 单节点数据总集，以obox序列号String为键，此键从Obox中获得
     */
    private static Map<String, List<ObNode>> nodesMap = new HashMap<>();
    /**
     * 组节点数据总集，以obox序列号String为键
     */
    private static Map<String, List<ObGroup>> groupsMap = new HashMap<>();
    /**
     * 情景数据总集，以obox序列号String为键
     */
    private static Map<String, List<ObScene>> scenMap = new HashMap<>();
    /**
     * 选定obox后的当前obox单节点数据集
     */
    private static List<ObNode> nodes;

    /**
     * 选定obox后的组节点数据集
     */
    private static List<ObGroup> obGroups;
    /**
     * 选定obox后的情景数据集
     */
    private static List<ObScene> scenes;

    public static Position getPosition() {
        return position;
    }

    public static void setPosition(Position position) {
        DataPool.position = position;
    }

    /**
     * 当前的position
     */
    private static Position position;

    /**
     * 对应obox单节点数据添加到本地
     *
     * @param obox    收集数据后生成的obox对象
     * @param obNodes 收集后的单节点数据集合
     */
    public static void putNodes(Obox obox, List<ObNode> obNodes) {
        nodesMap.put(obox.getObox_serial_id(), obNodes);
    }

    /**
     * 对应obox单节点数据添加到本地
     *
     * @param obox     收集数据后生成的obox对象
     * @param obGroups 收集后的组数据集合
     */
    public static void putGroups(Obox obox, List<ObGroup> obGroups) {
        groupsMap.put(obox.getObox_serial_id(), obGroups);
    }

    /**
     * 对应obox单节点数据添加到本地
     *
     * @param obox     收集数据后生成的obox对象
     * @param obScenes 收集后的情景数据集合
     */
    public static void putScenes(Obox obox, List<ObScene> obScenes) {
        scenMap.put(obox.getObox_serial_id(), obScenes);
    }

    /**
     * 选定obox后设定当前缓存的数据
     *
     * @param obox 选定当前obox
     */
    public static void setCurrent(Obox obox) {
        String key = obox.getObox_serial_id();
        nodes = nodesMap.get(key);
        scenes = scenMap.get(key);
        obGroups = groupsMap.get(key);
    }

    /**
     * 获取obnodes中的特定类类型列表
     *
     * @param type 类类型
     */
    public static List<ObNode> getNodesforType(Class type) {
        List<ObNode> nodesgoal = new ArrayList<>();
        for (ObNode obNode : nodes) {
            if (obNode.getClass() == type) {
                nodesgoal.add(obNode);
            }
        }
        return nodesgoal;
    }

    /**
     * 获取当前选定obox的所有节点数据
     */
    public static List<ObNode> getAllNodes() {
        return nodes;
    }

    /**
     * 获取当前选定obox的所有组节点数据
     */
    public static List<ObGroup> getObGroups() {
        return obGroups;
    }

    /**
     * 获取当前选定的obox中的所有情景
     */
    public static List<ObScene> getAllScenes() {
        return scenes;
    }

}
