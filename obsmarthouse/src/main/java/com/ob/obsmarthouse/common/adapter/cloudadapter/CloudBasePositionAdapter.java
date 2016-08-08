package com.ob.obsmarthouse.common.adapter.cloudadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.adapter.BasePositionAdapter;
import com.ob.obsmarthouse.common.bean.Position;

import java.util.List;

/**服务器版本的适配器
 * Created by adolf_dong on 2016/8/5.
 */
public class CloudBasePositionAdapter extends BasePositionAdapter {
    private List<Position> positions;

    public CloudBasePositionAdapter(Context context, List<Position> positions) {
        super(context);
        this.positions = positions;
    }

    @Override
    public int getCount() {
        return positions.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    protected Drawable onSetBackground(int position) {
        //noinspection deprecation
        return context.getResources().getDrawable(R.drawable.preview_position_back1);
    }

    @Override
    protected int onSetFirstPreview(int position) {
        // FIXME: 2016/8/5 资源缺失，暂定为lamp
        return R.drawable.led_rgb;
    }

    @Override
    public CharSequence onSetPositionText(int position) {
        return positions.get(position).getName();
    }
}
