package com.ob.obsmarthouse.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ob.obsmarthouse.R;

/**
 * 顶部控件
 * Created by adolf_dong on 2016/7/8.
 */
public class TopTitle extends RelativeLayout {

    private static final String TAG = "TopTitle";

    public static final int LEFT_POSITION = 0;
    public static final int RIGHT_POSITION = 1;
    /**
     * 左边按钮
     */
    private ImageView leftImg;
    /**
     * 顶部文字
     */
    private TextView title;
    /**
     * 右边按钮
     */
    private TextView rightText;


    /**
     * title文字
     */
    private String titleText;
    /**
     * title文字大小
     */
    private int titleTextSize;
    /**
     * title文字颜色
     */
    private int titleTextColor;

    /**
     * 左边按钮资源
     */
    private Drawable leftSrc;
    /**
     * 左边按钮宽度
     */
    private int leftBtnWidth;
    /**
     * 左边按钮高度
     */
    private int leftBtnHight;
    /**
     * 右边按钮引用资源
     */
    private String rightString;
    /**
     * 右边按钮宽度
     */
    private int rightTextColor;
    /**
     * 右边按钮高度
     */
    private int rightTextSize;
    private LeftOnClickLsn leftOnClickLsn = null;
    private RightOnClickLsn rightOnClickLsn = null;

    private LayoutParams leftImgParams, titleParams, righttParams;

    public TopTitle(Context context) {
        super(context);
    }

    public TopTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    public TopTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        getView(context);
        getPramater(context, attrs);
        usePramater();
        adddListner();
    }



    /**
     * 实例化控件
     */
    private void getView(Context context) {
        leftImg = new ImageView(context);
        title = new TextView(context);
        rightText = new TextView(context);
    }

    /**
     * 获取自定义参数
     */
    private void getPramater(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopTitle);
        titleText = a.getString(R.styleable.TopTitle_title_text);
        titleTextSize = a.getDimensionPixelSize(R.styleable.TopTitle_title_text_size, 32)/2;
        titleTextColor = a.getColor(R.styleable.TopTitle_title_text_color, context.getResources().getColor(R.color.white));
        leftSrc = a.getDrawable(R.styleable.TopTitle_left_btn_src);
        leftBtnWidth = (int) a.getDimension(R.styleable.TopTitle_left_btn_width, 80);
        leftBtnHight = (int) a.getDimension(R.styleable.TopTitle_left_btn_heigh, 80);
        rightString = a.getString(R.styleable.TopTitle_right_text_src);
        rightTextColor =  a.getColor(R.styleable.TopTitle_right_text_color, context.getResources().getColor(R.color.white));
        rightTextSize = (int) a.getDimension(R.styleable.TopTitle_right_text_size, 32)/2;
        a.recycle();
    }


    @SuppressWarnings("SuspiciousNameCombination")
    private void usePramater() {
        title.setText(titleText);
        title.setTextColor(titleTextColor);
        title.setTextSize(titleTextSize);
        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        leftImgParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftImgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        leftImgParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftImgParams.width =leftBtnWidth/2;
        leftImgParams.height = leftBtnHight/2;
        leftImgParams.setMargins(20, 0, 0, 0);
        leftImg.setImageDrawable(leftSrc);
        righttParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        righttParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        righttParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        righttParams.setMargins(0, 0, 20, 0);
        rightText.setText(rightString);
        rightText.setTextColor(rightTextColor);
        rightText.setTextSize(rightTextSize);
        addView(title, titleParams);
        addView(leftImg,leftImgParams);
        addView(rightText, righttParams);
        setBackgroundColor(getResources().getColor(R.color.blue));
    }


    public void setLeftOnClickLsn(LeftOnClickLsn leftOnClickLsn) {
        this.leftOnClickLsn = leftOnClickLsn;
    }
    public void setRightOnClickLsn(RightOnClickLsn rightOnClickLsn) {
        this.rightOnClickLsn = rightOnClickLsn;
    }

    private void adddListner(){
        leftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftOnClickLsn == null) {
                    return;
                }
                leftOnClickLsn.onLeftClick();
            }
        });
        rightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightOnClickLsn == null) {
                    return;
                }
                rightOnClickLsn.onRightClick();
            }
        });
    }

    /**是否展示两边按钮
     * @param position    {@link #LEFT_POSITION,#RIGHT_POSITION}
     * @param wantVisible 可见设置为true
     */
    public void setTopBtnVisible(int position,boolean wantVisible) {
        switch (position){
            case LEFT_POSITION:
                leftImg.setVisibility(wantVisible ? VISIBLE : INVISIBLE);
                break;
            case RIGHT_POSITION:
                rightText.setVisibility(wantVisible ? VISIBLE : INVISIBLE);
                break;
        }
    }

    /**设置顶部展现元素
     * @param titleString 显示文字
     * @param leftDraw 左边的图片资源 null则不可见
     * @param rightString 右边按钮字符串资源
     */
    public void setTopParamter(String titleString, Drawable leftDraw, String rightString) {
        title.setText(titleString);
        if (leftDraw == null) {
            leftImg.setVisibility(GONE);
        } else {
            leftImg.setImageDrawable(leftDraw);
        }
        if (rightString == null) {
            rightText.setVisibility(GONE);
        } else {
            rightText.setText(rightString);
        }
    }



    public interface LeftOnClickLsn {
        void onLeftClick();
    }
    public interface RightOnClickLsn {
        void onRightClick();
    }


}
