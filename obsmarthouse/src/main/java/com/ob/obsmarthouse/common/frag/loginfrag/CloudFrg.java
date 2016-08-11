package com.ob.obsmarthouse.common.frag.loginfrag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.act.MainAct;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.lsn.ChangeModeLSN;
import com.ob.obsmarthouse.common.net.cloudnet.GetParameter;
import com.ob.obsmarthouse.common.net.cloudnet.HttpRespond;
import com.ob.obsmarthouse.common.share.Share;
import com.ob.obsmarthouse.common.util.CloudParseUtil;
import com.ob.obsmarthouse.common.util.NetUtil;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * 登录界面远程登录
 * Created by adolf_dong on 2016/6/3.
 */
public class CloudFrg extends BaseFragment implements HttpRespond {
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
        /*
      账户图片
     */
        ImageView accountIv = (ImageView) view.findViewById(R.id.cloud_account).findViewById(R.id.img);
        /*
      账户文字
     */
        TextView accoutTv = (TextView) view.findViewById(R.id.cloud_account).findViewById(R.id.hint_tv);
        accountEt = (EditText) view.findViewById(R.id.cloud_account).findViewById(R.id.edit);
        accountEt.setText(Share.getString(OBConstant.StringKey.CLOUD_USER, getActivity()));
        /*
      密码图片
     */
        ImageView pwdIv = (ImageView) view.findViewById(R.id.cloud_pwd).findViewById(R.id.img);
        /*
      密码文字
     */
        TextView pwdTv = (TextView) view.findViewById(R.id.cloud_pwd).findViewById(R.id.hint_tv);
        pwdEt = (EditText) view.findViewById(R.id.cloud_pwd).findViewById(R.id.edit);
        pwdEt.setText(Share.getString(OBConstant.StringKey.CLOUD_PSW, getActivity()));
        pwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                if (!accountEt.getText().toString().equals("") && !pwdEt.getText().toString().equals("")) {
                    NetUtil.doCloudEven(CloudFrg.this, CloudConstant.CmdValue.LOGIN);
                } else {
                    showToat(getString(R.string.can_not_empty_input));
                }
            }
        });
    }


    @Override
    public void onRequest() {
        switch (GetParameter.ACTION) {
            case CloudConstant.CmdValue.LOGIN:
                showProgressDialog(getString(R.string.wait), getString(R.string.on_logtin), false);
                break;
        }
    }

    @Override
    public List<NameValuePair> getParamter() {
        switch (GetParameter.ACTION) {
            case CloudConstant.CmdValue.LOGIN:
                return GetParameter.onLogin(accountEt.getText().toString(), pwdEt.getText().toString());

        }
        return null;
    }

    @Override
    public void onSuccess(String json) {
        disMissProgressDialog();
        if (!CloudParseUtil.isSucceful(json)) {
            showToat(CloudParseUtil.getJsonParm(json, CloudConstant.ParameterKey.MSG));
            return;
        }
        switch (GetParameter.ACTION) {
            case CloudConstant.CmdValue.LOGIN:
                NetUtil.setCloudModeDetial(Integer.parseInt(CloudParseUtil.getJsonParm(json, CloudConstant.ParameterKey.WEIGHT)));
                GetParameter.ACCESSTOKEN = CloudParseUtil.getJsonParm(json, CloudConstant.ParameterKey.ACCESS_TOKEN);
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainAct.class);
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onFaild(Exception e) {
        disMissProgressDialog();
        showToat(getString(R.string.net_err));
    }

    @Override
    public void onFaild(int state) {
        disMissProgressDialog();
        showToat(getString(R.string.check_wifi_tips));
    }
}
