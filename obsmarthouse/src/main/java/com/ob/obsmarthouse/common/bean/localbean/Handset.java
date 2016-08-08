package com.ob.obsmarthouse.common.bean.localbean;

/**遥控器，对于app，遥控器的可视方面只有情景部分
 * Created by adolf_dong on 2016/7/1.
 */
public class Handset implements SceneCondition {
    @Override
    public int getconditionType() {
        return 0;
    }

    @Override
    public byte[] getCondition() {
        return new byte[0];
    }

    @Override
    public byte[] getConditionaddr() {
        return new byte[0];
    }
}
