package com.ob.obsmarthouse.common.util;

import com.ob.obsmarthouse.common.constant.CloudConstant;

import org.json.JSONException;
import org.json.JSONObject;

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
}
