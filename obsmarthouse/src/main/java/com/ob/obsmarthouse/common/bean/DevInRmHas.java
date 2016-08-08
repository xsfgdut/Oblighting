package com.ob.obsmarthouse.common.bean;

/**
 * 房间内拥有设备的种类
 * Created by adolf_dong on 2016/5/6.
 */
public class DevInRmHas {
    /**
     * 灯
     */
    private boolean hasLamp;
    /**
     * 空调
     */
    private boolean hasAirCon;
    /**
     * 电饭锅
     */
    private boolean hasElecooker;
    /**
     * 洗衣机
     */
    private boolean hasWashMch;
    /**
     * 窗帘
     */
    private boolean hasPurdah;

    /**
     * 插座
     */
    private boolean hasSocket;
    /**
     * 幕布
     */
    private boolean hasScreen;
    /**
     * 红外设备
     */
    private boolean hasInfraDev;

    /**
     * 传感器
     */
    private boolean hasSensor;

    public DevInRmHas() {

    }

    public DevInRmHas(boolean hasLamp, boolean hasAirCon,
                      boolean hasElecooker, boolean hasWashMch,
                      boolean hasPurdah, boolean hasSocket,
                      boolean hasScreen, boolean hasInfraDev,
                      boolean hasSensor) {
        this.hasLamp = hasLamp;
        this.hasAirCon = hasAirCon;
        this.hasElecooker = hasElecooker;
        this.hasWashMch = hasWashMch;
        this.hasPurdah = hasPurdah;
        this.hasSocket = hasSocket;
        this.hasScreen = hasScreen;
        this.hasInfraDev = hasInfraDev;
        this.hasSensor = hasSensor;
    }

    public boolean isHasLamp() {
        return hasLamp;
    }

    public void setHasLamp(boolean hasLamp) {
        this.hasLamp = hasLamp;
    }

    public boolean isHasAirCon() {
        return hasAirCon;
    }

    public void setHasAirCon(boolean hasAirCon) {
        this.hasAirCon = hasAirCon;
    }

    public boolean isHasElecooker() {
        return hasElecooker;
    }

    public void setHasElecooker(boolean hasElecooker) {
        this.hasElecooker = hasElecooker;
    }

    public boolean isHasWashMch() {
        return hasWashMch;
    }

    public void setHasWashMch(boolean hasWashMch) {
        this.hasWashMch = hasWashMch;
    }

    public boolean isHasPurdah() {
        return hasPurdah;
    }

    public void setHasPurdah(boolean hasPurdah) {
        this.hasPurdah = hasPurdah;
    }

    public boolean isHasSocket() {
        return hasSocket;
    }

    public void setHasSocket(boolean hasSocket) {
        this.hasSocket = hasSocket;
    }

    public boolean isHasScreen() {
        return hasScreen;
    }

    public void setHasScreen(boolean hasScreen) {
        this.hasScreen = hasScreen;
    }

    public boolean isHasInfraDev() {
        return hasInfraDev;
    }

    public void setHasInfraDev(boolean hasInfraDev) {
        this.hasInfraDev = hasInfraDev;
    }

    public boolean isHasSensor() {
        return hasSensor;
    }

    public void setHasSensor(boolean hasSensor) {
        this.hasSensor = hasSensor;
    }
}
