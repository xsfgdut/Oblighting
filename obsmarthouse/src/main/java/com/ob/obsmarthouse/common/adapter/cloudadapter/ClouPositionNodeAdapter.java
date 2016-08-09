package com.ob.obsmarthouse.common.adapter.cloudadapter;

import android.content.Context;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.BasePositionNodeAdapter;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.constant.OBConstant;

import java.util.List;

/**cloud mode positionNodeAdapter
 * Created by asus on 2016-8-9.
 */
public class ClouPositionNodeAdapter extends BasePositionNodeAdapter {
    private List<List<DeviceConfig>> deviceList;

    @Override
    public int getGroupCount() {
        return deviceList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return deviceList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deviceList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return deviceList.get(groupPosition).get(childPosition);
    }

    public ClouPositionNodeAdapter(Context context, List<List<DeviceConfig>> deviceList) {
        super(context);
        this.deviceList = deviceList;
    }



    @Override
    protected CharSequence onSetTypeTv(int groupPosition) {
        int type = Integer.parseInt(deviceList.get(groupPosition).get(0).getDevice_type());
        switch (type) {
            case OBConstant.NodeType.IS_LAMP:
                return context.getString(R.string.lamp);
        }
        return context.getString(R.string.lamp);
    }

    @Override
    protected int onSetTypeImg(int groupPosition) {
        int type = Integer.parseInt(deviceList.get(groupPosition).get(0).getDevice_type(),16);
        switch (type) {
            case OBConstant.NodeType.IS_LAMP:
                return R.drawable.equipment_led;
        }
        return R.drawable.equipment_led;
    }

    @Override
    protected CharSequence onSetDetailId(int groupPosition, int childPosition) {
        return deviceList.get(groupPosition).get(childPosition).getID();
    }

    @Override
    protected int onSetDetailImg(int groupPosition, int childPosition) {
        int type = Integer.parseInt(deviceList.get(groupPosition).get(childPosition).getDevice_child_type(), 16);
        switch (type) {
            case OBConstant.NodeType.IS_COLOR_LAMP:
                return R.drawable.led_rgb;
            case OBConstant.NodeType.IS_WARM_LAMP:
                return R.drawable.led_yellow;
            case OBConstant.NodeType.IS_SIMPLE_LAMP:
                return R.drawable.led_white;
        }
        return R.drawable.led_rgb;
    }

}
