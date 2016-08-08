package com.ob.obsmarthouse.common.bean.localbean;

/**情景的一般条件
 * Created by adolf_dong on 2016/7/1.
 */
public class Simple implements SceneCondition {
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
