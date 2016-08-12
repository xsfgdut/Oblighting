package com.ob.obsmarthouse.common.act;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DynamicPagerAdapter;
import com.ob.obsmarthouse.common.base.InteractiveBaseAct;
import com.ob.obsmarthouse.common.bean.User;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.data.DataPool;
import com.ob.obsmarthouse.common.frag.mainfrag.DeviceFragment;
import com.ob.obsmarthouse.common.frag.mainfrag.MyFragment;
import com.ob.obsmarthouse.common.frag.mainfrag.PositionFragment;
import com.ob.obsmarthouse.common.frag.mainfrag.SceneFragment;
import com.ob.obsmarthouse.common.net.cloudnet.GetParameter;
import com.ob.obsmarthouse.common.util.CloudParseUtil;
import com.ob.obsmarthouse.common.util.NetUtil;
import com.ob.obsmarthouse.common.widget.BottomTab;
import com.ob.obsmarthouse.common.widget.CustomViewPager;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * 主控制界面
 * 通过改变device scene room my 调整viewpager动态加载数序
 * Created by adolf_dong on 2016/5/5.
 */
public class MainAct extends InteractiveBaseAct {
    private static final String TAG = "MainAct";

    private BottomTab bottomTab;
    private CustomViewPager viewPager;
    private DeviceFragment deviceFragment;
    private SceneFragment sceneFragment;
    private PositionFragment positionFragment;
    private MyFragment myFragment;
    private DynamicPagerAdapter dynamicPagerAdapter;



    @Override
    protected void findView(Bundle savedInstanceState) {
        setContentView(R.layout.main_act);

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
        NetUtil.doCloudEven(this, CloudConstant.CmdValue.QUERY_DEVICE);
    }

    @Override
    public void onRequest() {

    }

    @Override
    public List<NameValuePair> getParamter() {
        switch (GetParameter.ACTION) {
            case CloudConstant.CmdValue.QUERY_DEVICE:
                return GetParameter.onQueryDevice(User.getUser(),0,0);
        }
        return null;
    }

    @Override
    public void onSuccess(String json) {
        switch (GetParameter.ACTION) {
            case CloudConstant.CmdValue.QUERY_DEVICE:
                CloudParseUtil.initDevice(json, DataPool.getDevices());
                break;
        }
        initView();
    }

    private void initView() {
        dynamicPagerAdapter = new DynamicPagerAdapter(getSupportFragmentManager());
        deviceFragment = DeviceFragment.instance();
        sceneFragment = SceneFragment.instance();
        positionFragment=  PositionFragment.instance();
        myFragment = MyFragment.instance();
        /*动态添加frag*/
        dynamicPagerAdapter.addFrag(deviceFragment);
        dynamicPagerAdapter.addFrag(sceneFragment);
        dynamicPagerAdapter.addFrag(positionFragment);
        dynamicPagerAdapter.addFrag(myFragment);
        bottomTab = (BottomTab) findViewById(R.id.main_act_bottomtab);
        int[] drawsrcs = {R.drawable.tab_equipment_normal,R.drawable.tab_scene_normal,R.drawable.tab_home_normal,R.drawable.tab_my_normal,R.drawable.tab_equipment_press,R.drawable.tab_scene_press,R.drawable.tab_home_press,R.drawable.tab_my_press};
        bottomTab.setParameter(drawsrcs);
        viewPager = (CustomViewPager) findViewById(R.id.main_act_vp);
        viewPager.setOffscreenPageLimit(3);
        bottomTab.changeShowView(0);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setAdapter(dynamicPagerAdapter);
        bottomTab.setBoottomTabLsn(new MyBoottomTabLsn());
    }

    @Override
    public void onReceive(Message message) {

    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            bottomTab.changeShowView(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyBoottomTabLsn implements BottomTab.BoottomTabLsn {
        @Override
        public void onTabClkLsn(int position) {
            viewPager.setCurrentItem(position,false);
        }
    }
}
