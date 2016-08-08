package com.ob.obsmarthouse.common.bean.localbean;

/**
 * 情景条件，除了可以为正常节点外还可以是定时和遥控器，所以之前各类均继承于此类
 * Created by adolf_dong on 2016/6/30.
 */
public interface SceneCondition {


    /**
     * 无条件
     */
    int SIMPLE = 0;
    /**
     * 定时
     */
    int TIMING = 1;
    /**
     * 传感器
     */
    int SENSOR = 2;
    /**
     * 遥控器
     */
    int CONTROL = 3;


    /**
     * 获取情景条件类型
     */
    int getconditionType();

    /**
     * 条件具体内容
     */
    byte[] getCondition();

    /**
     * 获取条件地址
     */
    byte[] getConditionaddr();

}
