package com.ob.obsmarthouse.common.bean.cloudbean;

import java.io.Serializable;

/**action的包装类，因为action没有节点种类
 * Created by adolf_dong on 2016/2/4.
 */
@Deprecated
public class ActionFC implements Serializable{

    /**
     * 节点类型
     */
    private String actionType;


    /**
     * 节点子类型
     */
    private String actionChildType;

    public ActionFC(){}

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    private Action action;

    public ActionFC(String actionType,String actionChildType, Action action) {
        this.actionType = actionType;
        this.actionChildType = actionChildType;
        this.action = action;
    }
    public String getActionChildType() {
        return actionChildType;
    }

    public void setActionChildType(String actionChildType) {
        this.actionChildType = actionChildType;
    }

}
