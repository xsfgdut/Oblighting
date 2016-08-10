package com.ob.obsmarthouse.common.frag.showNodefrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 单节点展示fragment
 * Created by adolf_dong on 2016/7/12.
 */
public abstract class ShowSingleFrag extends BaseFragment {
    protected static final String TAG = "ShowSingleFrag";
    protected ListView listView;

    public List<ObNode> getObNodes() {
        return obNodes;
    }

    /**
     * 本地单节点
     */
    protected List<ObNode> obNodes = new ArrayList<>();


    protected List<DeviceConfig> deviceConfigs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_single_frag, container, false);
    }

    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.single_listview);

    }

    @Override
    protected void onStationMode(View view, Bundle savedInstanceState) {

    }





}
