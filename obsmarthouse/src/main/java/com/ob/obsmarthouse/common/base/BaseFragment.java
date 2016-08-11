package com.ob.obsmarthouse.common.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.util.NetUtil;

/**
 * 基础交互
 * Created by adolf_dong on 2016/5/5.
 */
public abstract class BaseFragment extends Fragment {

    private ProgressDialog progressDialog;
    public static final int ONE = 1;
    public static final int TWO = 2;


    public void showToat(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    private static final String TAG = "BaseFragment";



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view, savedInstanceState);
        switch (getWorkMode()) {
            case OBConstant.NetState.ON_AP:
                // FIXME: 2016/7/12 ap模式和station模式统一处理
//                onApMode(view,savedInstanceState);
//                break;
            case OBConstant.NetState.ON_STATION:
                onStationMode(view, savedInstanceState);
                break;

            case OBConstant.NetState.ON_CLOUD:
                onCloudMode();
                break;
            default:
                break;
        }
    }

    /**
     * 处于station模式的操作
     *
     * @param view               ui实例
     * @param savedInstanceState bundle
     */
    protected abstract void onStationMode(View view, Bundle savedInstanceState);

    /**
     * 处于ap模式的操作
     *
     * @param view               ui实例
     * @param savedInstanceState bundle
     */
    protected abstract void onApMode(View view, Bundle savedInstanceState);

    /**
     * 处于服务器模式的操作
     */
    private void onCloudMode() {
        switch (NetUtil.getCloudModeDetial()) {
            case OBConstant.CloudDitalMode.SUPERROOT:
                onSuper();
                break;
            case OBConstant.CloudDitalMode.ROOT:
                onRoot();
                break;
            case OBConstant.CloudDitalMode.ADMIN:
                onAdmin();
                break;
            case OBConstant.CloudDitalMode.GUEST:
                onGuest();
                break;
        }
    }

    /**
     * 超级用户登陆
     */
    protected abstract void onSuper();

    /**
     * 总管理员登陆
     */
    protected abstract void onRoot();

    /**
     * 楼宇管理员登陆
     */
    protected abstract void onAdmin();


    /**
     * 访客
     */
    protected  abstract void onGuest();

    /**
     * 实例化页面控件
     *
     * @param view               ui实例
     * @param savedInstanceState bundle
     */
    protected abstract void findView(View view, Bundle savedInstanceState);

    /**
     * ProgressDialog交互
     *
     * @param title   标题
     * @param message 内容
     * @param cancel  是否可被取消
     */
    public void showProgressDialog(String title, String message, boolean cancel) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancel);
        progressDialog.show();
        progressDialog.setButton(ProgressDialog.BUTTON1,getString(R.string.cancel),new Message());

    }

    /**
     * 改变ProgressDialog展示的内容
     *
     * @param message 想要改变的内容
     */
    public void setDialogMessage(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
        }
    }

    /**
     * 设置dialog是否可被点击取消
     *
     * @param cancelAble 可以取消设置为true
     */
    public void setDialogCancelAble(boolean cancelAble) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setCancelable(cancelAble);
        }
    }

    /**
     * 取消当前展示的ProgressDialog
     */
    public void disMissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    /**
     * alert点击事件监听器
     */
    public interface SimpleDialogLSN {
        /**
         * 确认按钮点击执行
         */
        void pOnClick();

        /**
         * 取消按钮点击执行
         */
        void nOnClick();
    }

    /**
     * 显示一般的alertdialog
     *
     * @param mode            ONE,单个按钮，TWO,两个按钮
     * @param title           标题
     * @param msg             显示信息
     * @param pString         确认按钮文字
     * @param nString         取消按钮文字
     * @param simpleDialogLSN 确认和取消的两个点击事件
     */
    public void showSimpleDialog(int mode, String title, String msg, String pString, String nString, final SimpleDialogLSN simpleDialogLSN) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        alert.setMessage(msg);
        switch (mode) {
            case TWO:
                alert.setNegativeButton(nString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simpleDialogLSN.nOnClick();
                    }
                });
            case ONE:
                alert.setPositiveButton(pString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simpleDialogLSN.pOnClick();
                    }
                });
                break;
        }
        alert.show();

    }

    public interface ItemDialogLSN {
        void onItemDialogClick(int which);
    }

    /**
     * 显示ItemList类型alertdialog
     *
     * @param title         标题
     * @param msg           信息
     * @param items         显示数据String[]
     * @param itemDialogLSN 点击事件
     */
    public void showItemDialog(String title, String msg, String[] items, final ItemDialogLSN itemDialogLSN) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemDialogLSN.onItemDialogClick(which);
            }
        });
    }

    /**
     * @return 当前工作模式，OBCONSTANT station，ap，cloud
     */
    private int getWorkMode() {
        return NetUtil.getWorkMode();
    }


}
