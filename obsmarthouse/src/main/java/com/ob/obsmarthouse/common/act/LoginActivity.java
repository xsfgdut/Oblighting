package com.ob.obsmarthouse.common.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.frag.loginfrag.CloudFrg;
import com.ob.obsmarthouse.common.frag.loginfrag.LocalFrg;
import com.ob.obsmarthouse.common.util.NetUtil;

/**
 * 登录页面
 * 1、确定工作模式、local模式则搜索obox，
 * 2、初始化数据收集工具
 */
public  class LoginActivity extends BaseAct {
    private static final String TAG = "LoginActivity";

    private final int LOCAL = 0;
    private final int CLOUD = 1;
    private ViewPager vp;
    private LgPagerAdp fpa;
    private int workMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getView();
        getData();
        setAdp();
    }

    @Override
    protected void findView(Bundle savedInstanceState) {

    }

    @Override
    protected void onStationMode(Bundle savedInstanceState) {

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

    private void getData() {
        NetUtil netUtil = new NetUtil();
        workMode = netUtil.ckNet(this);
        fpa = new LgPagerAdp(getSupportFragmentManager());
    }

    private void setAdp() {
        vp.setAdapter(fpa);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                NetUtil.setWorkMode(position == 0 ?workMode:OBConstant.NetState.ON_CLOUD);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getView() {
        vp = (ViewPager) findViewById(R.id.vp);
    }

    private class LgPagerAdp extends FragmentPagerAdapter {

        public LgPagerAdp(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case LOCAL:
                    return LocalFrg.newInstance();
                case CLOUD:
                    return CloudFrg.newInstance();
            }
            return null;
        }

    }

    /**
     * 设置当前登录模式,并设置当前的工作模式
     *
     * @param isLocal 是否设置为本地登陆模式
     */
    public void setMode(boolean isLocal) {
        vp.setCurrentItem(isLocal ? LOCAL : CLOUD);
    }


}

