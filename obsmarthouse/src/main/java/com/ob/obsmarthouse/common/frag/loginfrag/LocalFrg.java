package com.ob.obsmarthouse.common.frag.loginfrag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.lsn.ChangeModeLSN;
import com.ob.obsmarthouse.common.net.localnet.Respond;

/**
 * 登录界面本地登陆
 * Created by adolf_dong on 2016/6/3.
 */
public class LocalFrg extends BaseFragment implements Respond{
    private static LocalFrg localFrg;
    @SuppressLint("ValidFragment")
    private  LocalFrg(){}
    public static LocalFrg newInstance() {
        synchronized (LocalFrg.class){
            if (localFrg == null) {
                localFrg= new LocalFrg();
            }
        }
        return localFrg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_local_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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


    @SuppressWarnings("deprecation")
    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        TextView toCloudtv = (TextView) view.findViewById(R.id.login_local_choose).findViewById(R.id.login_cloud_tv);
        toCloudtv.setTextColor(getResources().getColor(R.color.night_black));
        toCloudtv.setOnClickListener(new ChangeModeLSN(getActivity(), false));
    }




    @Override
    public void onReceive(Message message) {

    }
}
