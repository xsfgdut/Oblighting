package com.ob.obsmarthouse.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ob.obsmarthouse.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态加载viewPager页面适配器
 * Created by adolf_dong on 2016/7/12.
 */
public class DynamicPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> baseFragments = new ArrayList<>();
    public DynamicPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 添加适配器
     */
    public void addFrag(BaseFragment baseFragment) {
        if (baseFragment.isAdded())
            return;
        baseFragments.add(baseFragment);
    }

    @Override
    public int getCount() {
        return baseFragments.size();
    }

    private static final String TAG = "DynamicPagerAdapter";
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem() called with: " + "position = [" + position + "]");
        return baseFragments.get(position);
    }
}
