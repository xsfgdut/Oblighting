package com.ob.obsmarthouse.common.adapter.cloudadapter;

import android.content.Context;
import android.widget.Toast;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DrawBaseAdapter;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.constant.OBConstant;

import java.util.List;

/**服务器控制页面显示适配器,适配器通用，在frag中实现差异化
 * Created by adolf_dong on 2016/7/19.
 */
public class CloudShowSingleDrawAdapter extends DrawBaseAdapter{
    private List<DeviceConfig> deviceConfigs;

    public CloudShowSingleDrawAdapter(Context context, List<DeviceConfig> deviceConfigs) {
        super(context);
        this.deviceConfigs = deviceConfigs;
    }

    @Override
    protected boolean onsetScroolMode(int position) {
        return false;
    }

    @Override
    protected CharSequence onSetLeftHintText(int position) {
        return context.getString(R.string.draw_add);
    }

    @Override
    protected CharSequence onSetRightHintText(int position) {
        return context.getString(R.string.draw_delete);
    }

    @Override
    protected String setDrawText(int position) {
        return deviceConfigs.get(position).getID();
    }

    @Override
    protected int setDrawIcon(int position) {
        DeviceConfig deviceConfig = deviceConfigs.get(position);
        int type = Integer.parseInt(deviceConfig.getDevice_type(), 16);
        switch (type) {
            case OBConstant.NodeType.IS_LAMP:
                int childType = Integer.parseInt(deviceConfig.getDevice_child_type(), 16);
                switch (childType) {
                    case OBConstant.NodeType.IS_SIMPLE_LAMP:
                        return R.drawable.led_white;
                    case OBConstant.NodeType.IS_WARM_LAMP:
                        return R.drawable.led_yellow;
                    case OBConstant.NodeType.IS_COLOR_LAMP:
                        return R.drawable.led_rgb;
                }
                break;
        }
        return OBConstant.NodeType.IS_SIMPLE_LAMP;
    }

    @Override
    public Object getItem(int position) {
        return deviceConfigs.get(position);
    }


    @Override
    public int getCount() {
        return deviceConfigs.size();
    }


}
