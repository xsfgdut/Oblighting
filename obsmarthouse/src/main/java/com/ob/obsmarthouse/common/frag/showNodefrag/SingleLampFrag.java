package com.ob.obsmarthouse.common.frag.showNodefrag;

import android.os.Bundle;
import android.view.View;

import com.ob.obsmarthouse.common.adapter.DrawBaseAdapter;
import com.ob.obsmarthouse.common.adapter.cloudadapter.CloudShowSingleDrawAdapter;
import com.ob.obsmarthouse.common.adapter.localadapter.LocalShowSingleDrawAdapter;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.bean.localbean.Lamp;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.data.DataPool;

import java.util.List;

/**单灯节点frag
 * Created by adolf_dong on 2016/8/10.
 */
public class SingleLampFrag extends ShowSingleFrag {
    private DrawBaseAdapter drawBaseAdapter;


    @Override
    protected void onApMode(View view, Bundle savedInstanceState) {
    }

    @Override
    protected void onStationMode(View view, Bundle savedInstanceState) {
        obNodes = DataPool.getNodesforType(Lamp.class);
        drawBaseAdapter = new LocalShowSingleDrawAdapter(getActivity(), obNodes);
    }

    @Override
    protected void onSuper() {

    }

    @Override
    protected void onRoot() {

    }

    @Override
    protected void onAdmin() {

    }

    @Override
    protected void onGuest() {
        deviceConfigs = DataPool.getDevicesForType(OBConstant.NodeType.IS_LAMP);
        drawBaseAdapter = new CloudShowSingleDrawAdapter(getActivity(), deviceConfigs);
    }
}
