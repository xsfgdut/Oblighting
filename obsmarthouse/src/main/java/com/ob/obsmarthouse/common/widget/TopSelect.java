package com.ob.obsmarthouse.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ob.obsmarthouse.R;

/**
 * 选择viewPager显示选项
 * Created by adolf_dong on 2016/7/15.
 */
public class TopSelect extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "TopSelect";
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private Button leftBtn;
    private Button rightBtn;

    private View leftBelowLine;
    private View rightBelowLine;

    private LeftClick leftClick;
    private RightClick rightClick;

    public TopSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
        View rootView = LayoutInflater.from(context).inflate(R.layout.top_select, (ViewGroup) getRootView());
        leftBtn = (Button) rootView.findViewById(R.id.left_btn);
        rightBtn = (Button) rootView.findViewById(R.id.right_btn);
        leftBelowLine = rootView.findViewById(R.id.left_below_line);
        rightBelowLine = rootView.findViewById(R.id.right_below_line);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                onTopBtnClick(LEFT);
                if (leftClick != null) {
                    leftClick.leftClick();
                }
                break;
            case R.id.right_btn:
                onTopBtnClick(RIGHT);
                if (rightClick != null) {
                    rightClick.rightClick();
                }
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    private void onTopBtnClick(int position) {
        boolean isSingle = position == LEFT;
        leftBtn.setTextColor(isSingle ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.black));
        rightBtn.setTextColor(isSingle ? getResources().getColor(R.color.black) : getResources().getColor(R.color.blue));
        leftBelowLine.setVisibility(isSingle ? View.VISIBLE : View.INVISIBLE);
        rightBelowLine.setVisibility(isSingle ? View.INVISIBLE : View.VISIBLE);
    }

    /**设置两个按钮的显示文字，null则不改变
     * @param leftText 左边文字
     * @param rightText 右边文字
     */
    public void setParameter(String leftText,String rightText) {
        if (leftText!=null) {
            leftBtn.setText(leftText);
        }
        if (rightText!=null) {
            rightBtn.setText(rightText);
        }
    }
    /**
     * 设置左边点击按钮点击事件
     */
    public void setLeftClick(LeftClick leftClick) {
        this.leftClick = leftClick;
    }

    /**
     * 设置右边按钮点击事件
     */
    public void setRightClick(RightClick rightClick) {
        this.rightClick = rightClick;
    }

    /**
     * 左边点选
     */
    public interface LeftClick {
        void leftClick();
    }

    /**
     * 右边点选
     */
    public interface RightClick {
        void rightClick();
    }

}
