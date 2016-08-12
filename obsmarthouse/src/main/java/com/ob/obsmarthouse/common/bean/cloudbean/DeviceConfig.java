package com.ob.obsmarthouse.common.bean.cloudbean;

import com.ob.obsmarthouse.common.bean.PositionNode;
import com.ob.obsmarthouse.common.constant.OBConstant;
import com.ob.obsmarthouse.common.util.Transformation;

import java.io.UnsupportedEncodingException;

/**
 * 服务器版本设备
 * Created by adolf_dong on 2016/1/7.
 */
public class DeviceConfig extends Action implements  PositionNode {


    private transient float x;
    private transient float y;

    /**
     * 设备id
     */
    private String name;

    /**
     * 序列号
     */
    private String serialId;
    /**
     * 地址
     */
    private String addr;
    /**
     * 组地址
     */
    private String groupAddr;
    /**
     * 状态
     */
    private String state;
    /**
     * 类型
     */
    private String device_type;
    /**
     * 子类型
     */
    private String device_child_type ;
    /**
     * 版本号
     */
    private String version;

    /**
     * obox序列号
     */
    private String obox_serial_id;

    public DeviceConfig() {

    }

    public DeviceConfig(String name, String serialId, String addr, String groupAddr,
                        String state, String device_type,
                        String device_child_type,
                        String version) {
        this.name = name;
        this.serialId = serialId;
        this.addr = addr;
        this.groupAddr = groupAddr;
        this.state = state;
        this.device_type = device_type;
        this.device_child_type = device_child_type;
        this.version = version;
    }

    public DeviceConfig(byte[] name, byte[] serialId, byte addr, byte groupAddr,
                        byte[] state, byte device_type,
                        byte device_child_type,
                        byte[] version) {
        try {
            this.name = new String(name, OBConstant.StringKey.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.serialId = Transformation.byteArryToHexString(serialId);
        this.addr = String.valueOf(addr & 0xff);
        this.groupAddr = String.valueOf(groupAddr & 0xff);
        this.state = Transformation.byteArryToHexString(state);
        this.device_type = Transformation.byte2HexString(device_type);
        this.device_child_type = Transformation.byte2HexString(device_child_type);
        this.version = Transformation.byteArryToHexString(version);
    }

    public DeviceConfig(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getGroupAddr() {
        return groupAddr;
    }

    public void setGroupAddr(String groupAddr) {
        this.groupAddr = groupAddr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_child_type() {
        return device_child_type;
    }

    public void setDevice_child_type(String device_child_type) {
        this.device_child_type = device_child_type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getObox_serial_id() {
        return obox_serial_id;
    }

    public void setObox_serial_id(String obox_serial_id) {
        this.obox_serial_id = obox_serial_id;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    boolean inPosition = false;

    @Override
    public boolean inPosition() {
        return inPosition;
    }

    @Override
    public void setPosition(boolean inPosition) {
        this.inPosition = inPosition;
    }


}
