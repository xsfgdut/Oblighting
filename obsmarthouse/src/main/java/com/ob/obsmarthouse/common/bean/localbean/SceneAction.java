package com.ob.obsmarthouse.common.bean.localbean;

import java.util.HashMap;
import java.util.Map;

/**
 * 情景行为节点
 * Created by adolf_dong on 2016/7/1.
 */
public abstract class SceneAction {
    /**
     * 如果是情景内节点的时候此参数将被实例化
     * 代表其在某个情景内的响应状态
     * 键以当前情景序列号
     */
    private Map<String, byte[]> actions;

    /**
     * @param SceneSerNum 情景序列号
     */
    public byte[] getActions(int SceneSerNum) {
        if (actions == null) {
            actions = new HashMap<>();
        }
        String key = SceneSerNum + "";
        return actions.get(key);
    }

    public void putAction(int SceneSerNum, byte[] action) {
        if (actions == null) {
            actions = new HashMap<>();
        }
        String key = SceneSerNum + "";
        actions.put(key, action);
    }

    public abstract byte[] getAddrs();

}
