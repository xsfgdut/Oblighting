package com.ob.obsmarthouse.common.act;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DynamicPagerAdapter;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.bean.localbean.Cooker;
import com.ob.obsmarthouse.common.bean.localbean.Lamp;
import com.ob.obsmarthouse.common.bean.localbean.ObGroup;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.data.DataPool;
import com.ob.obsmarthouse.common.frag.controlfrag.GroupControlFrag;
import com.ob.obsmarthouse.common.frag.controlfrag.SingleControlFrag;
import com.ob.obsmarthouse.common.util.MathUtil;
import com.ob.obsmarthouse.common.widget.TopSelect;
import com.ob.obsmarthouse.common.widget.TopTitle;

import java.util.ArrayList;
import java.util.List;


/**
 * 选定某种特定设备展示页面，通过不同的适配器实现差异化
 */
public class ShowNodeAct extends BaseAct {

    private static final String TAG = "ShowNodeAct";

    /**
     * 顶部控件
     */
    private TopTitle topTitle;

    /**
     * 选择页面双按钮
     */
    private TopSelect topSelect;

    private ViewPager viewPager;

    /**
     * 本地单节点
     */
    private List<ObNode> obNodes = new ArrayList<>();

    /**
     * 本地组节点
     */
    private List<ObGroup> groups = new ArrayList<>();


    public List<ObNode> getObNodes() {
        return obNodes;
    }

    public List<ObGroup> getGroups() {
        return groups;
    }

    public TopTitle getTopTile() {
        return topTitle;
    }

    /**
     * 设定类型不同时候的不同处理方式
     * 页面数据的分类读取
     * 适配器的不同实现类
     * listView的不同点击事件
     */
    private void getData() {
        int nodeType = getIntent().getIntExtra(OBConstant.StringKey.NODETYPE, 0);
        switch (nodeType) {
            case OBConstant.NodeType.IS_LAMP:
                obNodes = DataPool.getNodesforType(Lamp.class);
                break;
            case OBConstant.NodeType.IS_COOKER:
                obNodes = DataPool.getNodesforType(Cooker.class);
                break;
            case OBConstant.NodeType.IS_HUMIDIFIER:
                break;
            case OBConstant.NodeType.IS_OBSOCKET:
                break;
            case OBConstant.NodeType.IS_CURTAIN:
                break;
            case OBConstant.NodeType.IS_FAN:
                break;
            case OBConstant.NodeType.IS_AIR_CLEAN:
                break;
            case OBConstant.NodeType.IS_TV:
                break;
            case OBConstant.NodeType.IS_SENSOR:
                break;
            case OBConstant.NodeType.AMMETER:
                break;
            case OBConstant.NodeType.AIR_CON:
                break;
            default:
                break;
        }
        MathUtil.makeShowData(obNodes, DataPool.getObGroups(), groups);
    }

    @Override
    protected void findView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_control);
        findView();
        addLsn();
    }

    @Override
    protected void onStationMode(Bundle savedInstanceState) {
        getData();
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
        BaseFragment singleFrag = new SingleControlFrag();
        BaseFragment groupFrag = new GroupControlFrag();
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
