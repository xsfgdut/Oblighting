package com.ob.obsmarthouse.common.bean.cloudbean;

import java.io.Serializable;
import java.util.Map;

/**
 * 情景内行为,单节点和组节点均对行为类拓展
 * 获取玩action后，依据唯一序列号和地址找到行为的具体设备实例
 * 对{@link com.ob.obsmarthouse.common.bean.cloudbean.CloudScene#actions}添加实例与并removeAction
 *
 * Created by adolf_dong on 2016/1/7.
 */
public class Action implements Serializable {
    /**
     * 行为id
     */
    private String action_id;
    /**
     * 具体行为
     */
    private String action;
    /**
     * 设备类型：组"80" 单节点"00"
     */
    private String node_type;

    /**并对<@link com.ob.obsmarthouse.common.bean.cloudbean.CloudScene#>
     * 对应各情景的具体行为，键以情景obox景序列号
     */
    private Map<String,String> actionDetialMap;

    public Action(){

    }


    public String getActionDetial(String key) {
        return actionDetialMap.get(key);
    }

    public void putActionDetial(String key,String val) {
        actionDetialMap.put(key, val);
    }
    public Action(String action_id, String action, String node_type) {

        this.action_id = action_id;
        this.action = action;
        this.node_type = node_type;
    }

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNode_type() {
        return node_type;
    }

    public void setNode_type(String node_type) {
        this.node_type = node_type;
    }


}
