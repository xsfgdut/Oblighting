package com.ob.obsmarthouse.common.frag.positionfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.act.CtrlLampAct;
import com.ob.obsmarthouse.common.adapter.BasePositionNodeAdapter;
import com.ob.obsmarthouse.common.adapter.cloudadapter.ClouPositionNodeAdapter;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.data.DataPool;

import java.util.ArrayList;
import java.util.List;

/** show all nodes in position ,not only type
 * Created by adolf_dong on 2016/8/9.
 */
public class PositionNodeFrag extends BaseFragment {

    private ExpandableListView expandableListView;
    private BasePositionNodeAdapter basePositionNodeAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.position_node_frag,container,false);
    }

    @Override
    protected void onStationMode(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void onApMode(View view, Bundle savedInstanceState) {

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

    }

    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        expandableListView = (ExpandableListView) view.findViewById(R.id.position_scene_frag_explv);
        basePositionNodeAdapter = new ClouPositionNodeAdapter(getActivity(), DataPool.getPositionDeviceList());
        expandableListView.setAdapter(basePositionNodeAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                DataPool.setDeviceConfig((DeviceConfig) basePositionNodeAdapter.getChild(groupPosition,childPosition));
                Intent intent = new Intent();
                switch (Integer.parseInt(DataPool.getDeviceConfig().getDevice_type(),16)) {
                    case OBConstant.NodeType.IS_LAMP:
                        intent.setClass(getActivity(), CtrlLampAct.class);
                        break;

                }
                startActivity(intent);
                return false;
            }
        });
    }
}
