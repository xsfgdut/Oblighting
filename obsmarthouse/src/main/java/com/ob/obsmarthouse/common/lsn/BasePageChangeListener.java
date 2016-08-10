package com.ob.obsmarthouse.common.lsn;

import android.support.v4.view.ViewPager;

import com.ob.obsmarthouse.common.act.PositionDetialShowAct;
import com.ob.obsmarthouse.common.widget.TopSelect;
import com.ob.obsmarthouse.common.widget.TopTitle;

/**实现了对TopSelect的视图控制
 * Created by adolf_dong on 2016/8/10.
 */
public class BasePageChangeListener implements ViewPager.OnPageChangeListener {
    private TopSelect topSelect;
    private OnPageSelectedLsn onPageSelectedLsn;
    public BasePageChangeListener( TopSelect topSelect) {
        this.topSelect = topSelect;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void setOnPageSelectedLsn(OnPageSelectedLsn onPageSelectedLsn) {
        this.onPageSelectedLsn = onPageSelectedLsn;
    }

    @Override
    public void onPageSelected(int position) {
        topSelect.onTopBtnClick(position);
        if (onPageSelectedLsn != null) {
            onPageSelectedLsn.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface OnPageSelectedLsn {
        void onPageSelected(int position);
    }
}
