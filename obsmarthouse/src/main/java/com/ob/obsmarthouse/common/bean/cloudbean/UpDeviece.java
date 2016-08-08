package com.ob.obsmarthouse.common.bean.cloudbean;

/**可以更新的设备
 * Created by adolf_dong on 2016/1/18.
 */
public class UpDeviece {
    /**
     * 设备ID
     */
    private  String ID;
    /**
     * 设备更新版本
     */
    private String version;
    /**
     * 更新下载地址
     */
    private String upgrade_url;

    public UpDeviece(String ID, String version, String upgrade_url) {
        this.ID = ID;
        this.version = version;
        this.upgrade_url = upgrade_url;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpgrade_url() {
        return upgrade_url;
    }

    public void setUpgrade_url(String upgrade_url) {
        this.upgrade_url = upgrade_url;
    }
}
