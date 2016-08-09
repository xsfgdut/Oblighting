package com.ob.obsmarthouse.common.act;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DynamicPagerAdapter;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.widget.TopSelect;
import com.ob.obsmarthouse.common.widget.TopTitle;

/**show position node and scene
 * Created by adolf_dong on 2016/8/9.
 */
public class PositionDetialShowAct extends BaseAct{
    private TopTitle topTitle;
    private TopSelect topSelect;
    private ViewPager viewPager;
    private DynamicPagerAdapter dynamicPagerAdapter;
    @Override
    protected void findView(Bundle savedInstanceState) {
        setContentView(R.layout.show_node_act);
        //noinspection deprecation
        topTitle = (TopTitle) findViewById(R.id.top_title);
        topTitle.setTopParamter(getString(R.string.position_room), getResources().getDrawable(R.drawable.back), getString(R.string.add_scene));
        topTitle.setTopBtnVisible(TopTitle.RIGHT_POSITION, false);
        topSelect = (TopSelect) findViewById(R.id.top_select);
        topSelect.setParameter(getString(R.string.device ),getString(R.string.scenn));
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        topSelect.setRightClick(new TopSelect.RightClick() {
            @Override
            public void rightClick() {
                topTitle.setTopBtnVisible(TopTitle.RIGHT_POSITION,true);
            }
        });
        topSelect.setLeftClick(new TopSelect.LeftClick() {
            @Override
            public void leftClick() {
                topTitle.setTopBtnVisible(TopTitle.RIGHT_POSITION,false);
            }
        });
        dynamicPagerAdapter = new DynamicPagerAdapter(getSupportFragmentManager());

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
