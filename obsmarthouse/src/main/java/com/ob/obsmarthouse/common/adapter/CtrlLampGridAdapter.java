package com.ob.obsmarthouse.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ob.obsmarthouse.R;

/**
 * 经典控制灯适配器
 * Created by adolf_dong on 2016/8/11.
 */
public class CtrlLampGridAdapter extends BaseAdapter {
    private Context context;
    private int[] imagSrcs;
    private int[] textSrcs;

    public CtrlLampGridAdapter(Context context) {
        this.context = context;
        textSrcs = new int[]{R.string.yzsg, R.string.wlhy, R.string.xxcs,
                R.string.xyxx, R.string.zrqx, R.string.qsnh, R.string.dhjl, R.string.jgss, R.string.prxd};
        imagSrcs = new int[]{R.drawable.h_red_circle, R.drawable.m_red_circle, R.drawable.l_red_circle, R.drawable.h_green_circle,
                R.drawable.m_green_circle, R.drawable.l_green_circle, R.drawable.h_blue_circle, R.drawable.m_blue_circle, R.drawable.l_blue_circle,
                R.drawable.color_snow, R.drawable.color_ocean, R.drawable.color_city,
                R.drawable.color_evening_sun, R.drawable.color_natural, R.drawable.select_color_year,
                R.drawable.color_wine, R.drawable.color_lightning, R.drawable.color_love};

    }

    @Override
    public int getCount() {
        return imagSrcs.length;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.classcial_ctrl_lamp_gv_item, null);
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ctrl_lamp_gv_img);
        viewHolder.textView = (TextView) convertView.findViewById(R.id.ctrl_lamp_gv_tv);
        if (position < 9) {
            //noinspection deprecation
            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(imagSrcs[position]));
        } else {
            viewHolder.imageView.setImageResource(imagSrcs[position]);
            viewHolder.textView.setText(context.getString(textSrcs[position - 9]));
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
