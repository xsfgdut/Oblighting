package com.ob.obsmarthouse.common.frag.ctrllampfrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.widget.ColorLampSettingPanel;
import com.ob.obsmarthouse.common.widget.SwitchButtonOfBule;

/**
 * 自定义控制灯节点frag
 * Created by asus on 2016-8-10.
 */
public class CustomCtrlLampFrag extends BaseFragment {
    private ColorLampSettingPanel colorLampSettingPanel;
    private SwitchButtonOfBule switchButtonOfBule;
    private SeekBar seekBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_ctrl_lamp_frag,container,false);
    }

    private static final String TAG = "CustomCtrlLampFrag";
    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        colorLampSettingPanel = (ColorLampSettingPanel) view.findViewById(R.id.custom_ctrl_panel);
        switchButtonOfBule = (SwitchButtonOfBule) view.findViewById(R.id.custom_ctrl_sb);
        seekBar = (SeekBar) view.findViewById(R.id.custom_ctrl_seekbar);
        colorLampSettingPanel.setView(3,11,11,11,12);

    }
    @Override
    protected void onStationMode(View view, Bundle savedInstanceState) {
        //noinspection deprecation

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
