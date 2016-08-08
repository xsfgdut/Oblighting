package com.ob.obsmarthouse.common.bean.localbean;

import com.ob.obsmarthouse.common.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地组标识,由于本地组和单节点只在
 * Created by Adolf_Dong on 2016/5/24.
 */
public class ObGroup extends SceneAction {
    private byte num;
    /**
     * 组地址
     */
    private byte addr;
    private byte[] id;
    private byte[] indexs;
    private List<Integer> indexList;
    /**
     * 组内节点
     */
    private List<ObNode> obNodes;
    /**
     * rf地址
     */
    private byte[] rfAddr;

    public byte getNum() {
        return num;
    }

    public List<Integer> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<Integer> indexList) {
        this.indexList = indexList;
    }

    public void setNum(byte num) {
        this.num = num;
    }

    public byte getAddr() {
        return addr;
    }

    public void setAddr(byte addr) {
        this.addr = addr;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    /**
     * 获得组索引数组
     */
    public byte[] getIndexs() {
        return indexs;
    }

    public void setIndexs(byte[] indexs) {
        this.indexs = indexs;
    }

    public ObGroup() {

    }

    public ObGroup(byte num, byte addr, byte[] id, byte[] indexs) {
        this.num = num;
        this.addr = addr;
        this.id = MathUtil.validArray(id);
        this.indexs = indexs;
        indexList = new ArrayList<>();
        MathUtil.index2List(indexs, indexList);
    }

    public List<ObNode> getObNodes() {
        if (obNodes == null) {
            obNodes = new ArrayList<>();
        }
        return obNodes;
    }

    public void setObNodes(List<ObNode> obNodes) {
        this.obNodes = obNodes;
    }

    public void setRfAddrs(byte[] rfAddr) {
        this.rfAddr = rfAddr;
    }


    @Override
    public byte[] getAddrs() {
        byte[] cplAddr = new byte[7];
        System.arraycopy(rfAddr, 0, cplAddr, 0, rfAddr.length);
        cplAddr[5] = addr;
        cplAddr[6] = (byte) 0xff;
        return cplAddr;
    }


    public byte[] getCplAddr() {
        return getAddrs();
    }


}
