package com.ob.obsmarthouse.common.bean.localbean;


import com.ob.obsmarthouse.common.util.MathUtil;

/**
 * 表示情景的定时属性
 */
public class Timing implements SceneCondition {
    /*循环，时区，等等*/
    public static int CIRCLE = 0;
    public static int TIMEZON = 1;
    public static int YEAR = 2;
    public static int MONTH = 3;
    public static int DAY = 4;
    public static int HOUR = 5;
    public static int MIN = 6;


    private static int ISALL = 7;
    private static int IS1 = 1;
    private static int IS2 = 2;
    private static int IS3 = 3;
    private static int IS4 = 4;
    private static int IS5 = 5;
    private static int IS6 = 6;
    private static int IS7 = 0;


    private byte[] time;

    public Timing(byte[] time) {
        this.time = time;
    }


    public byte[] getTime() {
        return time;
    }

    public void setTime(byte[] time) {
        this.time = time;
    }

    public int getStateforindex(int index) {
        return MathUtil.validByte(time[index]);
    }

    /**
     * 返回星期的某一天是否激活
     *
     * @param index
     * @return
     */
    public int getcircleStateforIndex(int index) {
        return MathUtil.byteIndexValid(time[CIRCLE], index);
    }



    /**
     * 设置条件的星期几参数
     * 表示是否希望开启当天的响应，注意bit7-bit0依次对应每天，6543217
     *
     * @param tag boolean数组，传入顺序周日，1-6，每天，
     */
    public void setCircle(boolean[] tag) {
        int rep = 0x80;
        if (tag[7]) {
            time[CIRCLE] = (byte) rep;
            return;
        }
        for (int i = 0; i < tag.length; i++) {
            rep += (tag[i] ? 1 : 0) << i;
        }
        time[CIRCLE] = (byte) rep;
    }

    @Override
    public int getconditionType() {
        return SceneCondition.TIMING;
    }


    @Override
    public byte[] getCondition() {
        return time;
    }

    @Override
    public byte[] getConditionaddr() {
        return new byte[0];
    }

}
