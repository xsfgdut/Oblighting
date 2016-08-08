package com.ob.obsmarthouse.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ob.obsmarthouse.R;

/**
 * 位置列表适配器
 * Created by adolf_dong on 2016/8/5.
 */
public abstract class BasePositionAdapter extends BaseAdapter {
    protected Context context;


    public BasePositionAdapter(Context context) {
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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.position_preview_item, null);
        convertView.setTag(viewHolder);
        viewHolder.positionText = (TextView) convertView.findViewById(R.id.position_preview_tv);
        viewHolder.lampPreview = (ImageView) convertView.findViewById(R.id.position_preview_iv);
        viewHolder.positionText.setText(onSetPositionText(position));
        viewHolder.lampPreview.setImageResource(onSetFirstPreview(position));
        //noinspection deprecation
        convertView.setBackgroundDrawable(onSetBackground(position));
        return convertView;
    }

    protected abstract Drawable onSetBackground(int position);

    protected abstract int onSetFirstPreview(int position);

    public abstract CharSequence onSetPositionText(int position);

    private class ViewHolder {
        /*位置字符*/
        private TextView positionText;
        /*灯节点预览*/
        private ImageView lampPreview;
    }

}
