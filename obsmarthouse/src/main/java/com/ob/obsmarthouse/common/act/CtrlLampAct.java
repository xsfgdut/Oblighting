package com.ob.obsmarthouse.common.act;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DynamicPagerAdapter;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.frag.ctrllampfrag.ClasscialCtrlLampFrag;
import com.ob.obsmarthouse.common.frag.ctrllampfrag.CustomCtrlLampFrag;
import com.ob.obsmarthouse.common.widget.TopSelect;
import com.ob.obsmarthouse.common.widget.TopTitle;

/**灯节点控制页面
 * Created by adolf_dong on 2016/8/4.
 */
public class CtrlLampAct extends BaseAct {
    private TopTitle topTitle;
    private TopSelect topSelect;
    private ViewPager viewPager;
    private DynamicPagerAdapter dynamicPagerAdapter;
    @Override
    protected void findView(Bundle savedInstanceState) {
        setContentView(R.layout.ctrllamp_act);
        topTitle = (TopTitle) findViewById(R.id.ctrl_lamp_top_title);
        //noinspection deprecation
        topTitle.setTopParamter(getString(R.string.lamp),getResources().getDrawable(R.drawable.back),null);
        topSelect = (TopSelect) findViewById(R.id.ctrl_lamp_top_select);
        viewPager = (ViewPager) findViewById(R.id.ctrl_lamp_viewpager);
        dynamicPagerAdapter = new DynamicPagerAdapter(getSupportFragmentManager());
        dynamicPagerAdapter.addFrag(new CustomCtrlLampFrag());
        dynamicPagerAdapter.addFrag(new ClasscialCtrlLampFrag());
        viewPager.setAdapter(dynamicPagerAdapter);
        topSelect.setViewPager(viewPager);
        topSelect.setParameter(getString(R.string.custom_ctrl_lamp),getString(R.string.classcial_ctrl_lamp));
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
