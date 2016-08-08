package com.ob.obsmarthouse.common.bean.cloudbean;

import java.util.List;

/**更新设备单元
 * Created by adolf_dong on 2016/1/18.
 */
public class Upgrade {
    /**
     * Obox是否有更新
     */
    private boolean upgrade;
    /**
     * Obox序列号
     */
    private String obox_serial_id;
    /**
     * Obox新版本号
     */
    private String obox_version;
    /**
     * 更新下载地址
     */
    private String upgrade_url;
    /**
     * 设备更新列表
     */
    private List<UpDeviece> device_upgrades;

    public Upgrade(boolean upgrade, String obox_serial_id, String obox_version, String upgrade_url, List<UpDeviece> device_upgrades) {
        this.upgrade = upgrade;
        this.obox_serial_id = obox_serial_id;
        this.obox_version = obox_version;
        this.upgrade_url = upgrade_url;
        this.device_upgrades = device_upgrades;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    public String getObox_serial_id() {
        return obox_serial_id;
    }

    public void setObox_serial_id(String obox_serial_id) {
        this.obox_serial_id = obox_serial_id;
    }

    public String getObox_version() {
        return obox_version;
    }

    public void setObox_version(String obox_version) {
        this.obox_version = obox_version;
    }

    public String getUpgrade_url() {
        return upgrade_url;
    }

    public void setUpgrade_url(String upgrade_url) {
        this.upgrade_url = upgrade_url;
    }

    public List<UpDeviece> getDevice_upgrades() {
        return device_upgrades;
    }

    public void setDevice_upgrades(List<UpDeviece> device_upgrades) {
        this.device_upgrades = device_upgrades;
    }
}
