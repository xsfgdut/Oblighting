package com.ob.obsmarthouse.common.frag.loginfrag;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.act.MainAct;
import com.ob.obsmarthouse.common.net.cloudnet.HttpRespond;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.lsn.ChangeModeLSN;
import com.ob.obsmarthouse.common.net.localnet.Respond;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * 登录界面远程登录
 * Created by adolf_dong on 2016/6/3.
 */
public class CloudFrg extends BaseFragment implements Respond,HttpRespond{
    /**
     * 账户图片
     */
    private ImageView accountIv;
    /**
     * 密码图片
     */
    private ImageView pwdIv;
    /**
     * 账户文字
     */
    private TextView accoutTv;
    /**
     * 密码文字
     */
    private TextView pwdTv;
    /**
     * 账户编辑
     */
    private EditText accountEt;
    /**
     * 密码编辑
     */
    private EditText pwdEt;

    @SuppressLint("ValidFragment")
    private CloudFrg() {
    }

    private static CloudFrg cloudFrg;

    public static CloudFrg newInstance() {
        synchronized (CloudFrg.class) {
            if (cloudFrg == null) {
                cloudFrg = new CloudFrg();
            }
        }
        return cloudFrg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_cloud_frag, container, false);
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
        accountIv = (ImageView) view.findViewById(R.id.cloud_account).findViewById(R.id.img);
        accoutTv = (TextView) view.findViewById(R.id.cloud_account).findViewById(R.id.hint_tv);
        accountEt = (EditText) view.findViewById(R.id.cloud_account).findViewById(R.id.edit);
        pwdIv = (ImageView) view.findViewById(R.id.cloud_pwd).findViewById(R.id.img);
        pwdTv = (TextView) view.findViewById(R.id.cloud_pwd).findViewById(R.id.hint_tv);
        pwdEt = (EditText) view.findViewById(R.id.cloud_pwd).findViewById(R.id.edit);
        accountIv.setImageResource(R.drawable.user);
        accoutTv.setText(R.string.login_cloud_account);
        pwdIv.setImageResource(R.drawable.password);
        pwdTv.setText(R.string.login_cloud_pwd);
        TextView toLocaltv = (TextView) view.findViewById(R.id.login_cloud_choose).
                findViewById(R.id.login_local_tv);
        toLocaltv.setTextColor(getResources().getColor(R.color.night_black));
        toLocaltv.setOnClickListener(new ChangeModeLSN(getActivity(), true));
        Button cloudBtn = (Button) view.findViewById(R.id.login_cloud_btn);
        cloudBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainAct.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }




    @Override
    public void onReceive(Message message) {

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
    public void onFaild(Exception e) {

    }

    @Override
    public void onFaild(int state) {

    }
}
