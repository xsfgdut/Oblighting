package com.ob.obsmarthouse.common.frag.positionfrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.BasePositionNodeAdapter;
import com.ob.obsmarthouse.common.base.BaseFragment;

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
    }
}
