package com.ob.obsmarthouse.common.frag.mainfrag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseFragment;

/**我的页面frag
 * Created by adolf_dong on 2016/5/5.
 */
public class MyFragment extends BaseFragment {

    private static MyFragment myFragment;
    private static final String TAG = "MyFragment";
    @SuppressLint("ValidFragment")
    private MyFragment() {

    }
    public static MyFragment instance() {
        synchronized (MyFragment.class){
            if (myFragment == null) {
                myFragment = new MyFragment();
            }
        }
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,TAG);
        return inflater.inflate(R.layout.my_frag,container,false);
    }

    @Override
    protected void onStationMode(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void onApMode(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void onSuper() {

    }

    @Override
    protected void onRoot() {

    }

    @Override
    protected void onAdmin() {

    }


    @Override
    protected void onGuest() {

    }


    @Override
    protected void findView(View view, Bundle savedInstanceState) {

    }
}
