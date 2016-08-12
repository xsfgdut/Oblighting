package com.ob.obsmarthouse.common.base;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.net.cloudnet.HttpRespond;
import com.ob.obsmarthouse.common.net.localnet.Respond;
import com.ob.obsmarthouse.common.util.CloudParseUtil;

/**实现网络交互接口并完成共性操作
 * Created by adolf_dong on 2016/8/12.
 */
public abstract class InteractiveBaseFragment extends BaseFragment implements HttpRespond, Respond {
    @Override
    public void operationFailed(String json) {
        showToat(CloudParseUtil.getJsonParm(json, CloudConstant.ParameterKey.MSG));
    }

    @Override
    public void onFaild(Exception e) {
        disMissProgressDialog();
        showToat(getString(R.string.net_err));
    }

    @Override
    public void onFaild(int state) {
        showToat(getString(R.string.check_wifi_tips));
    }

    @Override
    public void onRespond() {
        disMissProgressDialog();
    }
}
