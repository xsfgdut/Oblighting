package com.ob.obsmarthouse.common.frag.showNodefrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.bean.localbean.ObGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 组节点展示fragment
 * Created by adolf_dong on 2016/7/12.
 */
public abstract class ShowGroupFrag extends BaseFragment {
    protected static final String TAG = "ShowGroupFrag";
    protected ExpandableListView groupListView;


    public List<ObGroup> getGroups() {
        return groups;
    }

    /**
     * 本地组节点
     */
    protected List<ObGroup> groups = new ArrayList<>();



    protected List<List<DeviceConfig>> deviceArrayList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_group_frag, container, false);
    }



    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        groupListView = (ExpandableListView) view.findViewById(R.id.group_listview);
    }
}
