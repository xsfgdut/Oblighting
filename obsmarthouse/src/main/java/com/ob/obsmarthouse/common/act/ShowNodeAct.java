package com.ob.obsmarthouse.common.act;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DynamicPagerAdapter;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.data.DataPool;
import com.ob.obsmarthouse.common.frag.showNodefrag.GroupLampFrag;
import com.ob.obsmarthouse.common.frag.showNodefrag.ShowGroupFrag;
import com.ob.obsmarthouse.common.frag.showNodefrag.ShowSingleFrag;
import com.ob.obsmarthouse.common.frag.showNodefrag.SingleLampFrag;
import com.ob.obsmarthouse.common.util.MathUtil;
import com.ob.obsmarthouse.common.widget.TopSelect;
import com.ob.obsmarthouse.common.widget.TopTitle;


/**
 * 包含单节点组节点的show节点act
 * 在devicefragment点击跳到此展示页面，
 * 通过不同的fragmentpageAdapter实现差异化
 */
public class ShowNodeAct extends BaseAct {

    private static final String TAG = "ShowNodeAct";

    private ShowSingleFrag singleFrag;
    private ShowGroupFrag groupFrag;
    /**
     * 顶部控件
     */
    private TopTitle topTitle;

    /**
     * 选择页面双按钮
     */
    private TopSelect topSelect;

    private ViewPager viewPager;

    public TopTitle getTopTile() {
        return topTitle;
    }


    @Override
    protected void findView(Bundle savedInstanceState) {
        setContentView(R.layout.show_node_act);
        int nodetype = getIntent().getIntExtra(OBConstant.StringKey.NODETYPE, 0);
        switch (nodetype) {
            case OBConstant.NodeType.IS_LAMP:
                singleFrag = new SingleLampFrag();
                groupFrag = new GroupLampFrag();
                break;
        }
        findView();
        addLsn();
    }

    @Override
    protected void onStationMode(Bundle savedInstanceState) {
        MathUtil.makeShowData(singleFrag.getObNodes(), DataPool.getObGroups(), groupFrag.getGroups());
        setVpAdapter();
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



    private void setVpAdapter() {
        DynamicPagerAdapter controlPagerAdapter = new DynamicPagerAdapter(getSupportFragmentManager());
        controlPagerAdapter.addFrag(singleFrag);
        controlPagerAdapter.addFrag(groupFrag);
        viewPager.setAdapter(controlPagerAdapter);
    }

    private void findView() {
        topTitle = (TopTitle) findViewById(R.id.top_title);
        topSelect = (TopSelect) findViewById(R.id.top_select);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void addLsn() {
        topTitle.setLeftOnClickLsn(new TopTitle.LeftOnClickLsn() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }


}
