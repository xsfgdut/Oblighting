package com.ob.obsmarthouse.common.frag.mainfrag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DeviceBaseAdapter;
import com.ob.obsmarthouse.common.adapter.cloudadapter.CloudDeviceFragAdapter;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.widget.TopTitle;

/**
 * 设备页面frag
 * Created by adolf_dong on 2016/5/5.
 */
public class DeviceFragment extends BaseFragment {
    private static final String TAG = "DeviceFragment";

    private static DeviceFragment deviceFragment;
    private TopTitle topTitle;
    private ImageView imageView;
    private GridView gridLayout;
    private DeviceBaseAdapter deviceBaseAdapter;
    @SuppressLint("ValidFragment")
    private DeviceFragment() {
    }

    public static DeviceFragment instance() {
        synchronized (DeviceFragment.class) {
            if (deviceFragment == null) {
                deviceFragment = new DeviceFragment();
            }
        }
        return deviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.device_fragment, container, false);
    }

    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        topTitle = (TopTitle) view.findViewById(R.id.device_frag_tt);
        topTitle.setTopBtnVisible(TopTitle.LEFT_POSITION, false);
        imageView = (ImageView) view.findViewById(R.id.device_frag_iv);
        //noinspection deprecation
        imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.device_frag_top_bg));
        gridLayout = (GridView) view.findViewById(R.id.device_frag_gv);
        deviceBaseAdapter = new CloudDeviceFragAdapter(getActivity());
        gridLayout.setAdapter(deviceBaseAdapter);
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


}
