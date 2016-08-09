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

/**devicefragment show gridview adapter
 * Created by adolf_dong on 2016/8/9.
 */
public abstract class DeviceBaseAdapter extends BaseAdapter{
    protected Context context;

    public DeviceBaseAdapter(Context context) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.device_frag_gridview_item, null);
        viewHolder.nodeImg = (ImageView) convertView.findViewById(R.id.device_frag_gridview_iv);
        viewHolder.nodeNum = (TextView) convertView.findViewById(R.id.device_frag_gridview_num_tv);
        viewHolder.nodeType = (TextView) convertView.findViewById(R.id.device_frag_gridview_ndoetype_tv);
        viewHolder.nodeImg.setImageResource(onSetNodeImg(position));
        viewHolder.nodeNum.setText(onSetNodeNum(position));
        viewHolder.nodeType.setText(onSetNodeType(position));
        return convertView;
    }

    protected abstract CharSequence onSetNodeType(int position);

    protected abstract CharSequence onSetNodeNum(int position);

    protected abstract int onSetNodeImg(int position);

    class ViewHolder {
        ImageView nodeImg;
        TextView nodeNum;
        TextView nodeType;
    }
}
