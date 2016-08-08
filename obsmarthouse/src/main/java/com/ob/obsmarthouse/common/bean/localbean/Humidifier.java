package com.ob.obsmarthouse.common.bean.localbean;

/**加湿器
 * 状态  当前温度  当前湿度 目标湿度 定时剩余时间
 * Created by adolf_dong on 2016/6/23.
 */
public class Humidifier extends ObNode {
    public static final int STATE = 0;
    public static final int CURRENT_TEMP = 1;
    public static final int CURRENT_HUMI = 2;
    public static final int GOAL_HUMI = 3;
    public static final int TIMING = 4;
}
