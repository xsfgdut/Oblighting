package com.ob.obsmarthouse.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ob.obsmarthouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部tab集合
 * Created by adolf_dong on 2016/7/18.
 */
public class BottomTab extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "BottomTab";
    public static final int TAB_ONE = 0;
    public static final int TAB_TWO = 1;
    public static final int TAB_THREE = 2;
    public static final int TAB_FOUR = 3;
    private List<Tab> tabs = new ArrayList<>();
    private BoottomTabLsn mBoottomTabLsn;
    private int[] drawableSrcs;

    public BottomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        // FIXME: 2016/8/4 设定垂直方式
        View view = LayoutInflater.from(context).inflate(R.layout.bootom_tab, (ViewGroup) getRootView());
        Tab tab1 = (Tab) view.findViewById(R.id.tab_one);
        tab1.setOnClickListener(this);
        Tab tab2 = (Tab) view.findViewById(R.id.tab_two);
        tab2.setOnClickListener(this);
        Tab tab3 = (Tab) view.findViewById(R.id.tab_three);
        tab3.setOnClickListener(this);
        Tab tab4 = (Tab) view.findViewById(R.id.tab_four);
        tab4.setOnClickListener(this);
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);
        tabs.add(tab4);
    }


    /**
     * 获取图片和字符串资源
     *
     * @param drawableSrcs 图片资源数组
     */
    @SuppressWarnings("deprecation")
    public void setParameter(int[] drawableSrcs) {
        this.drawableSrcs = drawableSrcs;
        for (int i = 0; i < tabs.size(); i++) {
            Drawable drawable = getResources().getDrawable(drawableSrcs[i]);
            tabs.get(i).setParameter(drawable);
        }
    }


    /**
     * 改变当前的显示内容
     *
     * @param position {@link #TAB_ONE}{@link #TAB_TWO}{@link #TAB_THREE}{@link #TAB_FOUR}
     */
    @SuppressWarnings("deprecation")
    private void changeShowView(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            Tab tab = tabs.get(i);
            Drawable drawable = getResources().getDrawable(position == i ? drawableSrcs[i + 4] : drawableSrcs[i]);
            tab.setDrawable(drawable);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_one:
                changeShowView(TAB_ONE);
                break;
            case R.id.tab_two:
                changeShowView(TAB_TWO);
                break;
            case R.id.tab_three:
                changeShowView(TAB_THREE);
                break;
            case R.id.tab_four:
                changeShowView(TAB_FOUR);
                break;
        }
        if (mBoottomTabLsn == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.tab_one:
                mBoottomTabLsn.onTabClkLsn(TAB_ONE);
                break;
            case R.id.tab_two:
                mBoottomTabLsn.onTabClkLsn(TAB_TWO);
                break;
            case R.id.tab_three:
                mBoottomTabLsn.onTabClkLsn(TAB_THREE);
                break;
            case R.id.tab_four:
                mBoottomTabLsn.onTabClkLsn(TAB_FOUR);
                break;
        }
    }

    /**
     * 底部tab点击接口
     */
    public interface BoottomTabLsn {
        /**
         * @param position 被点击的tab位置
         */
        void onTabClkLsn(int position);
    }

    /**
     * 设置底部点击事件
     */
    public void setBoottomTabLsn(BoottomTabLsn mBoottomTabLsn) {
        this.mBoottomTabLsn = mBoottomTabLsn;
    }
}
