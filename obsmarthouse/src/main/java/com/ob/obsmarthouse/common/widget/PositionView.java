package com.ob.obsmarthouse.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.bean.PositionNode;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.constant.OBConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 房间内灯位置摆放视图
 * Created by adolf_dong on 2016/7/14.
 */
public class PositionView extends ImageView implements OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener {

    private static final String TAG = "PositionView";
    private Context context;
    /**
     * 在房间内的设备
     */
    private List<PositionNode> positionNodes;
    /**
     * 不在房间内的设备
     */
    private List<PositionNode> pstNodeRepo;
    private Paint paint = new Paint();
    private ScaleGestureDetector scaleGestureDetector = null;
    /**
     * 捕捉到的设备
     */
    private PositionNode positionNode;
    /**
     * 是否可触发move事件
     */
    boolean canMove = true;

    public PositionView(Context context) {
        super(context);
    }

    private boolean once = true;

    private Matrix mMatrix;
    private List<Bitmap> bitmaps;
    private float scale;
    float x = 0;
    float y = 0;
    boolean moveNode = true;
    /**
     * 记录被点击的节点
     */
    private int clickIndex;
    /**
     * 最小缩放
     */
    private float minScale;
    /**
     * 最大缩放
     */
    private float maxScale;

    public PositionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setScaleType(ScaleType.MATRIX);
        mMatrix = new Matrix();
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < positionNodes.size(); i++) {
            canvas.drawBitmap(bitmaps.get(i), positionNodes.get(i).getX(), positionNodes.get(i).getY(), paint);
        }
//        if (isEdit) {
//            for (int i = 0; i < pstNodeRepo.size(); i++) {
//                canvas.drawBitmap(bitmaps.get(i), pstNodeRepo.get(i).getX(), pstNodeRepo.get(i).getY(), paint);
//            }
//        }
    }

    public PositionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置不在房间内的待添加位置信息节点
     */
    public void setPstNodeRepo(List<PositionNode> pstNodeRepo) {
        this.pstNodeRepo = pstNodeRepo;
    }

    /**
     * 设置显示的资源
     *
     * @param drawableId    背景资源
     * @param deviceConfigs 节点显示
     */
    public void setShowParameter(int drawableId, List<PositionNode> deviceConfigs, List<PositionNode> pstNodeRepo) {
        this.positionNodes = deviceConfigs;
        this.pstNodeRepo = pstNodeRepo;
        int resId = 0;
        if (bitmaps == null) {
            bitmaps = new ArrayList<>();
        }
        //noinspection deprecation
        setBackgroundColor(getResources().getColor(R.color.black));
        for (int i = 0; i < deviceConfigs.size(); i++) {
            PositionNode positionNode = deviceConfigs.get(i);
            if (positionNode instanceof DeviceConfig) {
                DeviceConfig deviceConfig = (DeviceConfig) positionNode;
                if (deviceConfig.getDevice_child_type() == null) {
                    continue;
                }
                int type = Integer.parseInt(deviceConfig.getDevice_type(), 16);
                int childType = Integer.parseInt(deviceConfig.getDevice_child_type(), 16);
                switch (type) {
                    case OBConstant.NodeType.IS_LAMP:
                        switch (childType) {
                            case OBConstant.NodeType.IS_SIMPLE_LAMP:
                                resId = R.drawable.led_white;
                                break;
                            case OBConstant.NodeType.IS_WARM_LAMP:
                                resId = R.drawable.led_yellow;
                                break;
                            case OBConstant.NodeType.IS_COLOR_LAMP:
                                resId = R.drawable.led_rgb;
                                break;
                            default:
                                resId = R.drawable.led_rgb;
                                break;
                        }
                }
            } else if (positionNode instanceof ObNode) {
                Log.d(TAG, "");
                /*本地模式*/
            }
            bitmaps.add(BitmapFactory.decodeResource(getResources(), resId));
        }

    }

    private OnNodeClickLsn onNodeClickLsn;


    public interface OnNodeClickLsn {
        void OnNodeClick(int position);
    }


    /**
     * 设置节点点击的监听
     */
    public void setNodeIntentLsn(OnNodeClickLsn onNodeClickLsn) {
        this.onNodeClickLsn = onNodeClickLsn;
    }

    /**
     * 检查是否在边界
     */
    private void isOnBorder(MotionEvent event) {
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        float width = 0;
        float hight = 0;
        if (drawable != null) {
            width = drawable.getIntrinsicWidth();
            hight = drawable.getIntrinsicHeight();
        }
        rectF.set(0, 0, width, hight);
        mMatrix.mapRect(rectF);
        int w = getWidth();
        int h = getHeight();
        dx = event.getX() - x;
        dy = event.getY() - y;
        //上下越界
        if ((rectF.top +dy>= 0 && dy > 0)||(rectF.bottom -dy<= h && dy < 0) ) {
            dy = 0;
        }
        //左右越界
        if ((rectF.left+dx >= 0 && dx > 0)||(rectF.right-dx <= w && dx < 0)) {
            dx = 0;
        }
        x = event.getX();
        y = event.getY();
    }

    float dx;
    float dy;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        canMove = false;
        float scale = detector.getScaleFactor();
        float scaleBefore = getScaleCurrent();
        float px = detector.getFocusX();
        float py = detector.getFocusY();
        float wantScale = scale * scaleBefore;
        if (wantScale >= minScale && wantScale <= maxScale) {
            if (isEdit) {
            mMatrix.postScale(scale, scale, px, py);
            setImageMatrix(mMatrix);
            }
        }
        return true;
    }

    private float[] values = new float[9];

    /**
     * @return 获取当前的缩放比例
     */
    private float getScaleCurrent() {
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        canMove = false;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                canMove = true;
            }
        }, 1000);
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }


    @Override
    public void onGlobalLayout() {
        if (!once) {
            return;
        }
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        float windowW = getWidth();
        float windowH = getHeight();
        float scaleX = windowW / width;
        float scaleY = windowH / height;
        scale = Math.min(scaleY, scaleX);
        minScale = scale;
        maxScale = Math.max(scaleX, scaleY);
        Log.d(TAG, "minScale = " + minScale + "maxScale = " + maxScale);
        mMatrix.postTranslate((windowW - width) / 2, (windowH - height) / 2);
        mMatrix.postScale(scale, scale, windowW / 2, windowH / 2);
        setImageMatrix(mMatrix);
        once = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                moveNode = isClickNode(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (canMove) {
                    if (moveNode) {
                        if (!isEdit) {
                            return false;
                        }
                        positionNodes.get(clickIndex).setX((int) event.getX());
                        positionNodes.get(clickIndex).setY((int) event.getY());
                        invalidate();
                        break;
                    } else {
                        scrollView(event);
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() == x && event.getY() == y) {
                    if (isClickNode(event.getX(), event.getY())) {
                        if (onNodeClickLsn != null && !isEdit) {
                            onNodeClickLsn.OnNodeClick(clickIndex);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;

        }
        /*终止事件传递*/
        return true;
    }

    /**
     * 是否编辑状态
     */
    private boolean isEdit = false;

    /**
     * 设定当前的工作模式是否为可编辑模式
     */
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
        invalidate();
    }


    /**
     * croll页面
     */
    private void scrollView(MotionEvent event) {
        isOnBorder(event);
        /*重设所有的节点坐标*/
        for (int i = 0; i < positionNodes.size(); i++) {
            PositionNode positionNode = positionNodes.get(i);
            float x = positionNode.getX();
            float y = positionNode.getY();
            positionNode.setX(x + dx);
            positionNode.setY(y + dy);
        }
         /*对背景图片进行移动*/
        mMatrix.postTranslate(dx, dy);
        setImageMatrix(mMatrix);
    }


    /**
     * 寻找最近的节点，如果距离小于节点位图宽度，并且位于其范围内
     * 则捕获第一个拖拽
     * 否则执行背景图的拖拽
     * 是否捕捉到了节点
     */
    private boolean isClickNode(float x, float y) {
        for (int index = 0; index < bitmaps.size(); index++) {
            Bitmap bitmap = bitmaps.get(index);
            PositionNode positionNode = positionNodes.get(index);
            float currentX = positionNode.getX();
            float currentY = positionNode.getY();
            int bitmapWidth = bitmap.getWidth();
            int bitmaphight = bitmap.getHeight();
            float dx = x - currentX;
            float dy = y - currentY;
            if (dx > 0 && dy > 0 && dx < bitmapWidth && dy < bitmaphight) {
                clickIndex = index;
                return true;
            }
        }
        return false;
    }


}
