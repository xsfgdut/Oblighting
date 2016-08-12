package com.ob.obsmarthouse.common.frag.ctrllampfrag;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.CtrlLampGridAdapter;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.base.InteractiveBaseFragment;

import org.apache.http.NameValuePair;

import java.util.List;

/**经典调色方案
 * Created by asus on 2016-8-10.
 */
public class ClasscialCtrlLampFrag extends InteractiveBaseFragment {
    private GridView gridView;
    private CtrlLampGridAdapter ctrlLampGridAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.classcial_ctrl_lamp_frag,container,false);
    }

    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        gridView = (GridView) view.findViewById(R.id.classcial_ctrl_lamp_grid);
        ctrlLampGridAdapter = new CtrlLampGridAdapter(getActivity());
        gridView.setAdapter(ctrlLampGridAdapter);
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
    public void onRequest() {

    }

    @Override
    public List<NameValuePair> getParamter() {
        return null;
    }

    @Override
    public void onSuccess(String json) {

    }

    @Override
    public void onReceive(Message message) {

    }
}
