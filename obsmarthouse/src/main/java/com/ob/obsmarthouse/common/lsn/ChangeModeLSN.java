package com.ob.obsmarthouse.common.lsn;

import android.content.Context;
import android.view.View;

import com.ob.obsmarthouse.common.act.LoginActivity;

/**登录页设置工作模式页面
 * Created by adolf_dong on 2016/6/3.
 */
public class ChangeModeLSN implements View.OnClickListener {
    private boolean is2Local;
    private Context context;

    public ChangeModeLSN(Context context, boolean is2Local) {
        this.context = context;
        this.is2Local = is2Local;
    }
    @Override
    public void onClick(View v) {
        ((LoginActivity) context).setMode(is2Local);
    }
}
