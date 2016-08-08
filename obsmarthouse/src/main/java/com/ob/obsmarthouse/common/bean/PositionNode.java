package com.ob.obsmarthouse.common.bean;

import java.io.Serializable;

/**
 * 待定义内容，房间内节点
 * xy坐标轴，所属房间序列号
 * Created by adolf_dong on 2016/7/15.
 */
public interface PositionNode extends Serializable{
    float getX();

    void setX(float x);

    float getY();

    void setY(float y);

    /**
     * @return 是否已经有位置信息
     */
    boolean inPosition();

    /**设置位置信息有无
     */
    void setPosition(boolean isPosition);
}
