package com.ob.obsmarthouse.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * 滑动删除控件
 * Created by adolf_dong on 2016/5/30.
 */
public class DrawItem extends FrameLayout {

    private static final String TAG = "DrawItem";
    /**
     * 开始位置
     */
    private float startX;
    /**
     * 当前位置
     */
    private int currentX;
    /**
     * 结束位置
     */
    private int endX;


    /**
     * 当前水平滑动距离
     */
    private int cTransX;

    /**
     * 当前时间
     */
    private long cTime;

    private Context context;

    private FrameLayout.LayoutParams layoutParams;

    /**
     * 移动过
     */
    private boolean isMove = false;
    /**
     * 是否认为只隐藏底部右边文字
     */
    private boolean justRight;

    public boolean isScroll() {
        return isScroll;
    }

    /**
     * 打开状态
     */
    private boolean isScroll;

    public DrawItem(Context context) {
        super(context);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    /**执行此构造方法
     * @param context 上下文环境
     * @param attrs 自定义控件属性
     */
    public DrawItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        layoutParams = generateLayoutParams(attrs);
    }

    public DrawItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                getParent().getParent().requestDisallowInterceptTouchEvent(false);
                cTime = System.currentTimeMillis();
                startX = event.getX();
                isMove = false;
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                cTransX = (int) (startX - event.getX());
                // FIXME: 2016/6/1 去除触摸动画
//                long time = System.currentTimeMillis() -cTime;
//                if (time > 200 && layoutParams != null && cTransX > 0) {
//                    layoutParams.setMargins(-cTransX, 0, cTransX, 0);
//                    setLayoutParams(layoutParams);
//                    cTime = time;
//                }
                break;
            case MotionEvent.ACTION_UP:
                if (isMove) {
                    boolean cannotScroll = (cTransX < getWidth() / 10);
                    openOrClose(cannotScroll);
                }
                break;
        }
        return super.onTouchEvent(event);

    }

    /**
     * 打开或关闭scroll状态
     *
     * @param cannotScroll 是否可被打开
     */
    public void openOrClose(final boolean cannotScroll) {
        if (isScroll ^ cannotScroll) {
            return;
        }
        // FIXME: 2016/6/1 增加动画集，添加淡入淡出效果
        AnimationSet ans = new AnimationSet(context, null);
        Animation alpaAn = new AlphaAnimation(cannotScroll ? 0.5f : 1.0f, cannotScroll ? 1.0f : 0.5f);
        Animation animation = new TranslateAnimation(cTransX, 0, 0, 0);
        ans.addAnimation(alpaAn);
        ans.addAnimation(animation);
        ans.setDuration(300);
        setAnimation(ans);
        ans.startNow();
        layoutParams.setMargins(cannotScroll ? 0 : justRight?-getWidth() / 6:-getWidth() / 3, 0,
                cannotScroll ? 0 : justRight?getWidth() / 6:getWidth() / 3, 0);
        setLayoutParams(layoutParams);
        isScroll = !cannotScroll;
    }


    /**
     * 本函数在此控件添加到窗体的时候执行，在此时添加onclick事件，以解决父父控件ScrollListView不能触发ItemClick的问题
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMove) {
                    handOnItemClick();
                }
            }
        });
    }

    /**
     * 主动触发ScrollListView的ItemClick事件
     */
    private void handOnItemClick() {
        ViewParent vp = getParent().getParent();
        if (vp instanceof ScorllListView) {
            ScorllListView sl = (ScorllListView) vp;
            int position = sl.getPositionForView((View) getParent());
            if (!isScroll) {
                sl.performItemClick(sl.getChildAt(position - sl.getFirstVisiblePosition()), position,
                        sl.getAdapter().getItemId(position));
            } else {
                close();
            }
        }else{
            ListView sl = (ListView) vp;
            int position = sl.getPositionForView((View) getParent());
            if (!isScroll) {
                sl.performItemClick(sl.getChildAt(position - sl.getFirstVisiblePosition()), position,
                        sl.getAdapter().getItemId(position));
            } else {
                close();
            }
        }

    }


    public void close() {
        cTransX = justRight? -getWidth() / 6:-getWidth() / 3;
        openOrClose(true);
    }

    /**
     * @param justRight
     */
    public void setScroolMode(boolean justRight) {
        this.justRight = justRight;
    }

}
