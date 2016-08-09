package com.ob.obsmarthouse.common.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.bean.PositionNode;
import com.ob.obsmarthouse.common.bean.cloudbean.DeviceConfig;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.constant.CloudConstant;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.widget.PositionView;
import com.ob.obsmarthouse.common.widget.TopTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * 位置信息Act
 * Created by adolf_dong on 2016/7/21.
 */
public class PositionAct extends BaseAct {
    private ImageView tipImg;
    private TopTitle topTitle;
    private PositionView positionView;
    private boolean isEdit = false;
    private List<PositionNode> positionNodes;
    @Override
    protected void findView(Bundle savedInstanceState) {
        setContentView(R.layout.position_act);
        positionView = (PositionView) findViewById(R.id.position_position_view);
        tipImg = (ImageView) findViewById(R.id.position_act_tip_img);
        topTitle = (TopTitle) findViewById(R.id.position_title);
    }

    /**
     * 本地版本位置信息内节点点击事件
     */
    private void onLocalNodeClickLsn() {
        positionView.setNodeIntentLsn(new PositionView.OnNodeClickLsn() {
            @Override
            public void OnNodeClick(int position) {
                PositionNode positionNode = positionNodes.get(position);
                Intent intent = new Intent();
                 if (positionNode instanceof ObNode) {
                    ObNode obNode = (ObNode) positionNode;
                    switch (obNode.getParentType()) {
                        case OBConstant.NodeType.IS_LAMP:
                            intent.putExtra(OBConstant.StringKey.LAMP, obNode);
                            intent.setClass(PositionAct.this, CtrlLampAct.class);
                            break;
                    }
                }
            }
        });
    }

    /**
     * 服务器版本节点点击事件
     */
    private void onCloudNodeClickLsn() {
        positionView.setNodeIntentLsn(new PositionView.OnNodeClickLsn() {
            @Override
            public void OnNodeClick(int position) {
                PositionNode positionNode = positionNodes.get(position);
                Intent intent = new Intent();
                if (positionNode instanceof DeviceConfig) {
                    DeviceConfig deviceConfig = (DeviceConfig) positionNode;
                    switch (Integer.parseInt(deviceConfig.getDevice_type(),16)) {
                        case OBConstant.NodeType.IS_LAMP:
                            intent.putExtra(OBConstant.StringKey.LAMP, deviceConfig);
                            intent.setClass(PositionAct.this, CtrlLampAct.class);
                            break;
                    }
                }

            }
        });
    }


    @Override
    protected void onStationMode(Bundle savedInstanceState) {
        onLocalNodeClickLsn();
    }

    @Override
    protected void onSuper() {
        onNotGuest();
    }


    @Override
    protected void onRoot() {
        onNotGuest();
    }

    @Override
    protected void onAdmin() {
        onNotGuest();
    }

    @Override
    protected void onGuest() {
        setImgClickLsn();
        initPositionDataOnCloud();
        setTopLeftClickLsn();
        onCloudNodeClickLsn();
    }

    /**
     * 非访客模式
     */
    private void onNotGuest() {
        setEditClickLsn();
        setImgClickLsn();
        initPositionDataOnCloud();
        setTopLeftClickLsn();
        onCloudNodeClickLsn();
    }

    /**
     * 设置可编辑点击
     */
    private void setEditClickLsn() {
        topTitle.setRightOnClickLsn(new TopTitle.RightOnClickLsn() {
            @Override
            public void onRightClick() {
                isEdit = !isEdit;
                positionView.setIsEdit(isEdit);
                showToat(getString(isEdit ? R.string.pst_node_move_can : R.string.pst_node_move_can_not));
            }
        });
    }

    /**
     * 设置顶部返回点击事件
     */
    private void setTopLeftClickLsn() {
        topTitle.setLeftOnClickLsn(new TopTitle.LeftOnClickLsn() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    /**
     * 设置底部图片可点击
     */
    private void setImgClickLsn() {
        tipImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PositionAct.this, PositionDetialShowAct.class);
                startActivity(intent);
            }
        });
    }



    /**
     * 初始化positionView的数据
     */
    private void initPositionDataOnCloud() {
         positionNodes = new ArrayList<>();
        /*模拟数据*/
        for (int i = 0; i < 6; i++) {
            int x = i * 10;
            int y = i * 20;
            DeviceConfig deviceConfig = new DeviceConfig(x, y);
            deviceConfig.setDevice_type(CloudConstant.NodeType.IS_LAMP);
            deviceConfig.setDevice_child_type(CloudConstant.NodeType.IS_COLOUR);
            positionNodes.add(deviceConfig);
        }
        // FIXME: 2016/8/3 设置位置页面的参数，暂时设置为null，。通过link的inPosition判断是否要添加到已在
        positionView.setShowParameter(R.drawable.preview_position_back1, positionNodes, null);
    }

}
