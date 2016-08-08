package com.ob.obsmarthouse.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.widget.DrawItem;

/**
 * 滑动listView基础适配器适配器
 *
 */
public abstract class DrawBaseAdapter extends BaseAdapter {
    protected Context context;
    public DrawBaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }



    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SwipViewHd swipViewHd = new SwipViewHd();
        convertView = LayoutInflater.from(context).inflate(R.layout.draw_item, null);
        DrawItem drawItem = (DrawItem) convertView.findViewById(R.id.drawitem);
        drawItem.setScroolMode(onsetScroolMode(position));
        swipViewHd.drawIcon = (ImageView) convertView.findViewById(R.id.draw_icon);
        swipViewHd.drawText = (TextView) convertView.findViewById(R.id.draw_tv);
        swipViewHd.drawIcon.setImageResource(setDrawIcon(position));
        swipViewHd.drawText.setText(setDrawText(position));
        swipViewHd.leftHintText = (TextView) convertView.findViewById(R.id.hint_left_tv);
        swipViewHd.leftHintText.setText(onSetLeftHintText(position));
        swipViewHd.leftHintText.setOnClickListener(new LeftHindLSN(position));
        swipViewHd.rightHintText = (TextView) convertView.findViewById(R.id.hint_right_tv);
        swipViewHd.rightHintText.setText(onSetRightHintText(position));
        swipViewHd.rightHintText.setOnClickListener(new RightHindLSN(position));
        return convertView;
    }

    /**设置DrawItem滑动距离
     */
    protected abstract boolean onsetScroolMode(int position);

    /**设置左边隐藏文字
     */
    protected abstract CharSequence onSetLeftHintText(int position);
    /**设置右边隐藏文字
     */
    protected abstract CharSequence onSetRightHintText(int position);

    /**设置滑动组件的文字
     * @return 返回一个DrawAble
     * @param position 对应的位置
     */
    protected abstract String setDrawText(int position);

    /**设置滑动组件的icon
     * @param position 对应的位置
     * @return 设定文字
     */
    protected abstract int setDrawIcon(int position);





    class SwipViewHd {
        ImageView drawIcon;
        TextView drawText;
        TextView leftHintText;
        TextView rightHintText;
    }

    private LeftHindClickLsn leftHindClickLsn;
    private RightHindClickLsn rightHindClickLsn;

    public interface LeftHindClickLsn{
        /**当左边隐藏部分被点击的时候执行
         * @param position listview中的position
         */
        void onLeftHindClick(int position);
    }
    public interface RightHindClickLsn{
        /**当右边隐藏部分被点击的时候执行
         * @param position listview中的position
         */
        void onRightHindClick(int position);
    }

    public  void setOnLeftHindClickLsn(LeftHindClickLsn leftHindClickLsn) {
        this.leftHindClickLsn = leftHindClickLsn;
    }

    public  void setOnRightHindClickLsn(RightHindClickLsn rightHindClickLsn){
        this.rightHindClickLsn = rightHindClickLsn;
    }

    private class LeftHindLSN implements View.OnClickListener {
        private int position;
        LeftHindLSN(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            if (leftHindClickLsn!=null) {
                leftHindClickLsn.onLeftHindClick(position);
            }
        }
    }

    private class RightHindLSN implements View.OnClickListener {
        private int position;
        RightHindLSN(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            if (rightHindClickLsn!=null) {
                rightHindClickLsn.onRightHindClick(position);
            }
        }
    }
}