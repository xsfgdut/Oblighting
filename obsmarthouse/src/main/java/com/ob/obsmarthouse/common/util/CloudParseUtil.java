package com.ob.obsmarthouse.common.util;

import com.google.gson.Gson;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.constant.CloudConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**解析返回的
 * Created by adolf_dong on 2016/7/20.
 */
public class CloudParseUtil {


    /**
     * 检测回复的json状态
     *
     * @param json 传入json数据
     * @return 回复是否成功
     */
    public static boolean isSucceful(String json) {
        JSONObject jsonObject = null;
        boolean succeful = false;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return false;
        }
        try {
            succeful = jsonObject.getBoolean(CloudConstant.ParameterKey.IS_SUCCESS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return succeful;
    }
    /**
     * 获得对应的字符串参数
     *
     * @param json 传入json数据
     * @param key  example {@link CloudConstant.ParameterKey#ACCESS_TOKEN}
     * @return 对应的字符串参数
     */
    public static String getJsonParm(String json,String key) {
        JSONObject jsonObject = null;
        String val = "null";
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return val;
        }
        try {
            val = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return val;
    }

    public static void initDevice(String json, List<DeviceConfig> deviceConfigs) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(CloudConstant.ParameterKey.CONFIG);
            for (int i = 0; i < jsonArray.length(); i++) {
                DeviceConfig deviceConfig = gson.fromJson(jsonArray.getString(i), DeviceConfig.class);
                deviceConfigs.add(deviceConfig);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
