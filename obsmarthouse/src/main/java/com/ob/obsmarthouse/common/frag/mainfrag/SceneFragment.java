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

/**
 * 场景frag
 * Created by adolf_dong on 2016/5/5.
 */
public class SceneFragment extends BaseFragment {
    private static final String TAG = "SceneFragment";

    private static SceneFragment sceneFragment;

    @SuppressLint("ValidFragment")
    private SceneFragment() {

    }

    public static SceneFragment instance() {
        synchronized (SceneFragment.class) {
            if (sceneFragment == null) {
                sceneFragment = new SceneFragment();
            }
        }
        return sceneFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scene_fragment, container, false);
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
