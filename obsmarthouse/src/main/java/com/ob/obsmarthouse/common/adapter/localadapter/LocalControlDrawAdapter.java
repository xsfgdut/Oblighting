package com.ob.obsmarthouse.common.adapter.localadapter;

import android.content.Context;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.DrawBaseAdapter;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.constant.OBConstant;

import java.util.List;

/**
 * 本地模式控制页面显示滑动适配器
 * Created by adolf_dong on 2016/7/19.
 */
public class LocalControlDrawAdapter extends DrawBaseAdapter {
    private List<ObNode> obNodes;

    public LocalControlDrawAdapter(Context context, List<ObNode> obNodes) {
        super(context);
        this.obNodes = obNodes;
    }

    @Override
    public Object getItem(int position) {
        return obNodes.get(position);
    }

    @Override
    public int getCount() {
        return obNodes.size();
    }


    @Override
    protected boolean onsetScroolMode(int position) {
        return false;
    }

    @Override
    protected CharSequence onSetLeftHintText(int position) {
        return null;
    }

    @Override
    protected CharSequence onSetRightHintText(int position) {
        return null;
    }

    @Override
    protected String setDrawText(int position) {
        return obNodes.get(position).getNodeId();
    }

    @Override
    protected int setDrawIcon(int position) {
        switch (obNodes.get(position).getParentType()) {
            case OBConstant.NodeType.IS_LAMP:
                switch (obNodes.get(position).getType()) {
                    case OBConstant.NodeType.IS_SIMPLE_LAMP:
                        return R.drawable.led_white;
                    case OBConstant.NodeType.IS_WARM_LAMP:
                        return R.drawable.led_yellow;
                    case OBConstant.NodeType.IS_COLOR_LAMP:
                        return R.drawable.led_rgb;
                }
                break;

        }
        return R.drawable.led_rgb;
    }

}
