package com.ob.obsmarthouse.common.bean.localbean;

import com.ob.obsmarthouse.common.util.MathUtil;

/**传感器
 * Created by adolf_dong on 2016/6/23.
 */
public class ObSensor extends ObNode implements SceneCondition{
    /*以下四项参数可直接满足als co flood radar，此四类设备不需拓展*/
    public static final int AUTO = 0;
    public static final int REPSTALL = 1;
    public static final int STALL = 2;
    public static final int VOLTAGE = 3;

    /**获取传感器的自动上报开关位
     * @param tag 要取开关位的index,对于单条件的传感器，传0即可
     * @return 上报打开返回true，否则false
     */
    public boolean getIndexRep(int tag ) {
        return MathUtil.byteIndexValid(getState()[AUTO], tag) == 1;
    }

    /**按序号依次设置开关,如为人体感应器，则先传雷达再传红外。
     * 如为环境传感器，则依次传入光，温，湿开关位
     * @param tag 开关位boolean数组
     */
    public void setRep(boolean[] tag){
        int rep = 0;
        for (int i = 0; i < tag.length; i++) {
            rep += (tag[i]?1:0)<<i;
        }
        getState()[AUTO] = (byte) rep;
    }

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
