package com.ob.obsmarthouse.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.widget.DrawItem;

/**
 * base adapter of positionNodeFrag's ExpandableListView
 * Created by adolf_dong on 2016/8/9.
 */
public abstract class BasePositionNodeAdapter extends BaseExpandableListAdapter {
    protected Context context;

    public BasePositionNodeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = new GroupViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.position_node_type_item, null);
        groupViewHolder.groupTypeImg = (ImageView) convertView.findViewById(R.id.position_node_type_img);
        groupViewHolder.groupTypeTv = (TextView) convertView.findViewById(R.id.position_node_type_tv);
        groupViewHolder.groupTypeImg.setImageResource(onSetTypeImg(groupPosition));
        groupViewHolder.groupTypeTv.setText(onSetTypeTv(groupPosition));
        return convertView;
    }

    protected abstract CharSequence onSetTypeTv(int groupPosition);

    protected abstract int onSetTypeImg(int groupPosition);

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.draw_item, null);
        viewHolder.lefTv = (TextView) convertView.findViewById(R.id.hint_left_tv);
        viewHolder.lefTv.setText(onSetLeftText(groupPosition, childPosition));
        viewHolder.lefTv.setOnClickListener(new OnChildLeftClickLsn(groupPosition, childPosition));
        viewHolder.rightTv = (TextView) convertView.findViewById(R.id.hint_right_tv);
        viewHolder.rightTv.setText(onSetRightText(groupPosition, childPosition));
        viewHolder.drawItem = (DrawItem) convertView.findViewById(R.id.drawitem);
        viewHolder.drawItem.setScroolMode(onSetChildScroolMode(groupPosition, childPosition));
        ImageView icon = (ImageView) viewHolder.drawItem.findViewById(R.id.draw_icon);
        TextView textView = (TextView) viewHolder.drawItem.findViewById(R.id.draw_tv);
        icon.setImageResource(onSetDetailImg(groupPosition, childPosition));
        textView.setText(onSetDetailId(groupPosition, childPosition));
        return convertView;
    }

    protected abstract CharSequence onSetRightText(int groupPosition, int childPosition);

    protected abstract CharSequence onSetLeftText(int groupPosition, int childPosition);

    protected abstract boolean onSetChildScroolMode(int groupPosition, int childPosition);

    protected abstract CharSequence onSetDetailId(int groupPosition, int childPosition);

    protected abstract int onSetDetailImg(int groupPosition, int childPosition);

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    class GroupViewHolder {
        ImageView groupTypeImg;
        TextView groupTypeTv;
    }

    class ViewHolder {
        TextView lefTv;
        TextView rightTv;
        DrawItem drawItem;
    }

    private ChildLeftClickLsn childLeftClickLsn;

    public void setChildLeftClickLsn(ChildLeftClickLsn childLeftClickLsn) {
        this.childLeftClickLsn = childLeftClickLsn;
    }
    public interface ChildLeftClickLsn {
        void OnChildLeftClick(int groupPosition, int childPosition);
    }

    private class OnChildLeftClickLsn implements View.OnClickListener {

        private int groupPosition;
        private int childPosition;

        public OnChildLeftClickLsn(int groupPosition, int childPosition) {

            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            if (childLeftClickLsn != null) {
                childLeftClickLsn.OnChildLeftClick(groupPosition, childPosition);
            }
        }
    }
}
