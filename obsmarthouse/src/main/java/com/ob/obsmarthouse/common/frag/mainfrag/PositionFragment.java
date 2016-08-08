package com.ob.obsmarthouse.common.frag.mainfrag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.act.CreatPositionAct;
import com.ob.obsmarthouse.common.act.PositionAct;
import com.ob.obsmarthouse.common.adapter.BasePositionAdapter;
import com.ob.obsmarthouse.common.adapter.cloudadapter.CloudBasePositionAdapter;
import com.ob.obsmarthouse.common.base.BaseFragment;
import com.ob.obsmarthouse.common.bean.Position;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.data.DataPool;
import com.ob.obsmarthouse.common.widget.TopTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * 房间frag
 * Created by adolf_dong on 2016/5/5.
 */
public class PositionFragment extends BaseFragment {
    private static final String TAG = "PositionFragment";
    private TopTitle topTitle;
    private ListView listView;
    private List<Position> positions;
    private BasePositionAdapter basePositionAdapter;
    private static PositionFragment positionFragment;


    @SuppressLint("ValidFragment")
    private PositionFragment() {

    }

    public static PositionFragment instance() {
        synchronized (PositionFragment.class) {
            if (positionFragment == null) {
                positionFragment = new PositionFragment();
            }
        }
        return positionFragment;
    }

    @SuppressWarnings("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CloudConstant.TransKey.CREAT_POSITON:

            }
        }
    };

    public Handler getHandler() {
        return handler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.position_fragment, container, false);
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

    private void initPositionData() {
        positions = new ArrayList<>();
        Position position = new Position();
        position.setName(getString(R.string.my_position));
        positions.add(position);
        basePositionAdapter = new CloudBasePositionAdapter(getActivity(), positions);
    }


    @Override
    protected void findView(View view, Bundle savedInstanceState) {
        topTitle = (TopTitle) view.findViewById(R.id.position_frag_tt);
        topTitle.setTopBtnVisible(TopTitle.LEFT_POSITION, false);
        topTitle.setRightOnClickLsn(new TopTitle.RightOnClickLsn() {
            @Override
            public void onRightClick() {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CreatPositionAct.class);
                startActivity(intent);
            }
        });
        listView = (ListView) view.findViewById(R.id.position_frag_lv);
        initPositionData();
        listView.setAdapter(basePositionAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                DataPool.setPosition(positions.get(position));
                intent.setClass(getActivity(), PositionAct.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DataPool.getPosition()!=null) {
            Log.d(TAG, DataPool.getPosition().getName());
        }
    }
}
