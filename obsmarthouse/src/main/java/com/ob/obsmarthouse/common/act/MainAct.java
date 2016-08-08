package com.ob.obsmarthouse.common.act;

import android.os.Bundle;
import android.util.Log;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DynamicPagerAdapter;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.frag.mainfrag.DeviceFragment;
import com.ob.obsmarthouse.common.frag.mainfrag.MyFragment;
import com.ob.obsmarthouse.common.frag.mainfrag.PositionFragment;
import com.ob.obsmarthouse.common.frag.mainfrag.SceneFragment;
import com.ob.obsmarthouse.common.widget.BottomTab;
import com.ob.obsmarthouse.common.widget.CustomViewPager;

/**
 * 主控制界面
 * 通过改变device scene room my 调整viewpager动态加载数序
 * Created by adolf_dong on 2016/5/5.
 */
public class MainAct extends BaseAct {
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
        dynamicPagerAdapter = new DynamicPagerAdapter(getSupportFragmentManager());
        deviceFragment = DeviceFragment.instance();
        dynamicPagerAdapter.addFrag(deviceFragment);
        sceneFragment = SceneFragment.instance();
        dynamicPagerAdapter.addFrag(sceneFragment);
        positionFragment=  PositionFragment.instance();
        dynamicPagerAdapter.addFrag(positionFragment);
        myFragment = MyFragment.instance();
        dynamicPagerAdapter.addFrag(myFragment);
        bottomTab = (BottomTab) findViewById(R.id.main_act_bottomtab);
        int[] drawsrcs = {R.drawable.tab_equipment_normal,R.drawable.tab_scene_normal,R.drawable.tab_home_normal,R.drawable.tab_my_normal,R.drawable.tab_equipment_press,R.drawable.tab_scene_press,R.drawable.tab_home_press,R.drawable.tab_my_press};
        bottomTab.setParameter(drawsrcs);
        viewPager = (CustomViewPager) findViewById(R.id.main_act_vp);
        viewPager.setAdapter(dynamicPagerAdapter);
        bottomTab.setBoottomTabLsn(new BottomTab.BoottomTabLsn() {
            @Override
            public void onTabClkLsn(int position) {
                viewPager.setCurrentItem(position,false);
            }
        });
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

}
