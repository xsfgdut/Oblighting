package com.ob.obsmarthouse.common.lsn;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.widget.DrawItem;

/**
 * 滑动删除监听，实现doItent做目标操作
 * Created by adolf_dong on 2016/6/1.
 */
public abstract class DrawItemLsn implements AdapterView.OnItemClickListener {
    private Context context;
    private ListView lv;

    public DrawItemLsn(ListView lv, Context context) {
        this.lv = lv;
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int count = parent.getCount();
        boolean isAllClos = true;
        for (int i = 0; i < count; i++) {
            FrameLayout fl = (FrameLayout) lv.getChildAt(i);
            if (fl == null) {
                continue;
            }
            DrawItem di = (DrawItem) fl.findViewById(R.id.drawitem);
            if (di.isScroll()) {
                di.close();
                isAllClos = false;
            }
        }
        if (isAllClos) {
            onItemClick(context, position);
        }
    }

    /**listview被点击并且没有被打开的
     * @param context 上下文
     * @param position 被点击的位置
     */
    public abstract void onItemClick(Context context, int position);
}
