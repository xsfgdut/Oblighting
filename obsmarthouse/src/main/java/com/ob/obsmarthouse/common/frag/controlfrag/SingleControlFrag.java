package com.ob.obsmarthouse.common.frag.controlfrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseFragment;

/**
 * 单节点展示fragment
 * Created by adolf_dong on 2016/7/12.
 */
public class SingleControlFrag extends BaseFragment {
    private static final String TAG = "SingleControlFrag";
    private ListView listView;
    private BaseAdapter singleAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.control_single_frag, container, false);
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
        listView = (ListView) view.findViewById(R.id.single_listview);
    }
}
