package com.ob.obsmarthouse.common.adapter.localadapter;

import android.content.Context;

import com.ob.obsmarthouse.common.adapter.BasePositionNodeAdapter;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;

import java.util.List;

/**local mode PositionNodeAdapter
 * Created by asus on 2016-8-9.
 */
public class LocalPositionNodeAdapter extends BasePositionNodeAdapter {
    private List<List<ObNode>> lists;
    @Override
    protected CharSequence onSetTypeTv(int groupPosition) {
        return null;
    }

    public LocalPositionNodeAdapter(Context context, List<List<ObNode>> lists) {
        super(context);
        this.lists = lists;
    }

    @Override
    protected int onSetTypeImg(int groupPosition) {
        return 0;
    }

    @Override
    protected CharSequence onSetRightText(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    protected CharSequence onSetLeftText(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    protected boolean onSetChildScroolMode(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    protected CharSequence onSetDetailId(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    protected int onSetDetailImg(int groupPosition, int childPosition) {
        return 0;
    }
}
