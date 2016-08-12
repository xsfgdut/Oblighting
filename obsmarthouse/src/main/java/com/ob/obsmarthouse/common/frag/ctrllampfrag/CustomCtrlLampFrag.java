package com.ob.obsmarthouse.common.frag.ctrllampfrag;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.InteractiveBaseFragment;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.data.DataPool;
import com.ob.obsmarthouse.common.net.cloudnet.GetParameter;
import com.ob.obsmarthouse.common.util.NetUtil;
import com.ob.obsmarthouse.common.util.StringUtil;
import com.ob.obsmarthouse.common.util.Transformation;
import com.ob.obsmarthouse.common.widget.ColorLampSettingPanel;
import com.ob.obsmarthouse.common.widget.SwitchButtonOfBule;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * 自定义控制灯节点frag
 * Created by asus on 2016-8-10.
 */
public class CustomCtrlLampFrag extends InteractiveBaseFragment {
    private static final String TAG = "CustomCtrlLampFrag";

    private ColorLampSettingPanel colorLampSettingPanel;
    private SwitchButtonOfBule switchButtonOfBule;
    private SeekBar seekBar;
    private int type;
    private DeviceConfig deviceConfig;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_ctrl_lamp_frag,container,false);
    }

    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        colorLampSettingPanel = (ColorLampSettingPanel) view.findViewById(R.id.custom_ctrl_panel);
        switchButtonOfBule = (SwitchButtonOfBule) view.findViewById(R.id.custom_ctrl_sb);
        seekBar = (SeekBar) view.findViewById(R.id.custom_ctrl_seekbar);
        deviceConfig = DataPool.getDeviceConfig();
        type = Integer.parseInt(deviceConfig.getDevice_child_type());
        colorLampSettingPanel.setView(type, 0, 0, 0, 0);
        seekBar.setOnSeekBarChangeListener(new CloudSeekBarChangeLsn());
        colorLampSettingPanel.setOnColorChangeLsn(new CloudColorChangeLsn());
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


    @Override
    public void onRequest() {

    }

    @Override
    public List<NameValuePair> getParamter() {
        switch (GetParameter.ACTION) {
            case CloudConstant.CmdValue.SETTING_NODE_STATUS:
                return GetParameter.onSetNodeState(deviceConfig,false);
        }
        return null;
    }

    @Override
    public void onSuccess(String json) {

    }

    @Override
    public void onReceive(Message message) {

    }

    private static class CloudColorChangeLsn implements ColorLampSettingPanel.OnColorChangeLsn {
        @Override
        public void onWarmChange(int cool) {

        }

        @Override
        public void onColorChange(int red, int green, int blue) {

        }
    }

    private class CloudSeekBarChangeLsn implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (type) {
                case OBConstant.NodeType.IS_COLOR_LAMP:
                    deviceConfig.setState(Transformation.byte2HexString((byte) progress)+deviceConfig.getState().substring(2, 12));
                    break;
                case OBConstant.NodeType.IS_WARM_LAMP:
                    int warmLight = (int) ((progress * 126f / 100) + 128);
                    deviceConfig.setState(Transformation.byte2HexString((byte) warmLight)+deviceConfig.getState().substring(2, 12));
                    break;
            }
            NetUtil.doCloudEven(CustomCtrlLampFrag.this, CloudConstant.CmdValue.SETTING_NODE_STATUS);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
