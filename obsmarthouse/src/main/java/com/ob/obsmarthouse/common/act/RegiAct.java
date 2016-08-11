package com.ob.obsmarthouse.common.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.net.cloudnet.GetParameter;
import com.ob.obsmarthouse.common.net.cloudnet.HttpRespond;
import com.ob.obsmarthouse.common.util.CloudParseUtil;
import com.ob.obsmarthouse.common.util.NetState;
import com.ob.obsmarthouse.common.util.NetUtil;
import com.ob.obsmarthouse.common.util.ParseUtil;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * 注册界面
 * Created by adolf_dong on 2016/5/13.
 */
public class RegiAct extends BaseAct implements HttpRespond {
    /**
     * 注册图片
     */
    private ImageView accountImg;
    /**
     * 密码图片
     */
    private ImageView pswImg;
    /**
     * 验证码图片
     */
    private ImageView codeImg;

    /**
     * 用户id
     */
    private EditText idEt;
    /**
     * 用户密码
     */
    private EditText pswEt;
    /**
     * 验证码
     */
    private EditText codeEt;
    /**
     * 激活按钮
     */
    private Button activateBtn;
    /**
     * 返回按钮
     */
    private ImageView fnsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_regist);
        getView();

    }

    private void getView() {
        accountImg = (ImageView) (findViewById(R.id.regist_id).findViewById(R.id.regist_img));
        accountImg.setImageResource(R.mipmap.regist_account);
        pswImg = (ImageView) (findViewById(R.id.regist_psw).findViewById(R.id.regist_img));
        pswImg.setImageResource(R.mipmap.regist_psw);
//        codeImg = (ImageView) (findViewById(R.id.regist_code).findViewById(R.id.regist_img));
//        codeImg.setImageResource(R.mipmap.regist_secur_code);
        idEt = (EditText) (findViewById(R.id.regist_id).findViewById(R.id.regist_edt));
        idEt.setHint(R.string.regi_hint_account);
        pswEt = (EditText) (findViewById(R.id.regist_psw).findViewById(R.id.regist_edt));
        pswEt.setHint(R.string.regi_hint_psw);
//        codeEt = (EditText) (findViewById(R.id.regist_code).findViewById(R.id.regist_edt));
//        codeEt.setHint(R.string.regi_hint_secur_code);
        activateBtn = (Button) findViewById(R.id.activate_btn);
        activateBtn.setOnClickListener(new RegLsn());
        fnsBtn = (ImageView) findViewById(R.id.setting_title_btn);
        fnsBtn.setOnClickListener(new FnsLsn());
    }


    @Override
    public void onRequest() {
        showProgressDialog(getString(R.string.tips_title), getString(R.string.on_do), false);
    }

    @Override
    public List<NameValuePair> getParamter() {
        switch (GetParameter.ACTION) {
            case CloudConstant.CmdValue.REGISTER:
                return GetParameter.onRegister(idEt.getText().toString(), pswEt.getText().toString());
        }
        return null;
    }

    @Override
    public void onSuccess(String json) {
        disMissProgressDialog();
        switch (GetParameter.ACTION) {
            case CloudConstant.CmdValue.REGISTER:
                if (CloudParseUtil.isSucceful(json)) {
                    showToat(getString(R.string.regi_suc));
                } else {
                    showToat(getString(R.string.regi_fal));
                }
                break;
        }
    }

    @Override
    public void onFaild(Exception e) {
        onfalDo();
    }

    @Override
    public void onFaild(int state) {
        onfalDo();
    }

    private class RegLsn implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            NetUtil.doCloudEven(RegiAct.this, CloudConstant.CmdValue.REGISTER);
        }
    }

    private class FnsLsn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
