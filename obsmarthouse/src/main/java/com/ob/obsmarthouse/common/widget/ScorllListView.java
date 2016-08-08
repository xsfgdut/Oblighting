package com.ob.obsmarthouse.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**暂时保留，待新需求重载事件传递方法
 * Created by adolf_dong on 2016/5/31.
 */
public class ScorllListView extends ListView {
    private static final String TAG = "ScorllListView";
    private int position;
    private boolean allClose =true;

    public ScorllListView(Context context) {
        super(context);
    }

    public ScorllListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScorllListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }


    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setAllClose(boolean allClose) {
        this.allClose = allClose;
    }

    public boolean getAllClose() {
        return allClose;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                if (!getAllClose()){
//                    return true;
//                }
//        }
        return super.onTouchEvent(ev);
    }
}
