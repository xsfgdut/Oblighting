package com.ob.obsmarthouse.common.adapter.cloudadapter;

import android.content.Context;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DeviceBaseAdapter;

/**cloud mode devicefragment gridview adapter
 * Created by adolf_dong on 2016/8/9.
 */
public class CloudDeviceFragAdapter extends DeviceBaseAdapter{
    int[] nodeImgs ;
    int[] nodeTypes;
    public CloudDeviceFragAdapter(Context context) {
        super(context);
        nodeTypes = new int[]{R.string.lamp,R.string.sensor,R.string.wind_curtain,
                R.string.obox,R.string.curtain,R.string.socket,R.string.air_con};
        nodeImgs = new int[]{R.drawable.equipment_led,R.drawable.equipment_sensor,R.drawable.equipment_window_curtain,
                R.drawable.equip_obox,R.drawable.equip_curtian_unable,R.drawable.equip_socket_unable,
                R.drawable.equip_aircon_unable};
    }

    @Override
    public int getCount() {
        return nodeTypes.length;
    }

    @Override
    protected CharSequence onSetNodeType(int position) {
        return context.getResources().getString(nodeTypes[position]);
    }

    @Override
    protected CharSequence onSetNodeNum(int position) {
        return String.valueOf(0);
    }

    @Override
    protected int onSetNodeImg(int position) {
        return nodeImgs[position];
    }
}
