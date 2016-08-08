package com.ob.obsmarthouse.common.data;

import com.ob.obsmarthouse.common.bean.cloudbean.Obox;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器版本数据
 * Created by adolf_dong on 2016/7/19.
 */
public class CloudDataPool {

    private static  List<Obox> oboxList;

    /**获取当前的obox列表
     */
    public static List<Obox> getOboxList() {
        if (oboxList == null) {
            oboxList = new ArrayList<>();
        }
        return oboxList;
    }

    /**添加obox到当前列表
     */
    public static void addObox(Obox obox) {
        if (oboxList== null) {
            oboxList = new ArrayList<>();
        }
        oboxList.add(obox);
    }
}
