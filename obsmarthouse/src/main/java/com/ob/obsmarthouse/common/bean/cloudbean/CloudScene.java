package com.ob.obsmarthouse.common.bean.cloudbean;

import java.io.Serializable;
import java.util.List;

/**
 * 云版本情景
 * Created by adolf_dong on 2016/1/7.
 */
public class CloudScene implements Serializable{

    /**
     * 场景类型，值为-1、无使能场景，0、无条件场景，1、定时场景，2、传感器场景
     */
    private String scene_type;
    /**
     * 场景id，用以修改场景名，信息的唯一标识
     */
    private String scene_id;
    /**
     * 场景序列号
     */
    private String scene_number = "0";
    /**
     * 条件为定时的时候全为0
     */
    private String condition_id = "0000000000000000";
    /**
     * 条件
     */
    private String condition;

    /**
     * 场景的使能状态 enable disable
     */
    private String scene_status;

    private String remoter_index = "0";

    /**
     * 行为列表,拿到action后，实例化
     */
    private List<Action> actions;

    public CloudScene(String scene_type, String scene_id, String scene_number, String condition_id,
                      String condition, List<Action> actions, String scene_status) {
        this.scene_type = scene_type;
        this.scene_id = scene_id;
        this.scene_number = scene_number;
        this.condition_id = condition_id;
        this.condition = condition;
        this.actions = actions;
        this.scene_status = scene_status;
    }

    /**
     * 用于新建情景
     */
    public CloudScene(){}

    public String getScene_type() {
        return scene_type;
    }

    public void setScene_type(String scene_type) {
        this.scene_type = scene_type;
    }

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    public String getScene_number() {
        return scene_number;
    }

    public void setScene_number(String scene_number) {
        this.scene_number = scene_number;
    }

    public String getCondition_id() {
        return condition_id;
    }

    public void setCondition_id(String condition_id) {
        this.condition_id = condition_id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * 获取action后和本地操作一样 ，寻找具体的action
     * */
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getScene_status() {
        return scene_status;
    }

    public void setScene_status(String scene_status) {
        this.scene_status = scene_status;
    }

    public String getRemoter_index() {
        return remoter_index;
    }

    public void setRemoter_index(String remoter_index) {
        this.remoter_index = remoter_index;
    }
}



