package com.ob.obsmarthouse.common.bean.cloudbean;

import com.ob.obsmarthouse.common.util.Transformation;

import java.io.Serializable;
import java.util.List;

/**服务器和本地都会用到这个类
 * obox
 * Created by adolf_dong on 2016/1/7.
 */
public class Obox implements Serializable{

    /**
     * obox的id，也是obox的唯一标识
     */
    private String obox_serial_id;
    /**
     * obox的版本号
     */
    private String obox_version;
    /**
     * obox的名字
     */
    private String obox_name;
    /**
     * obxo的密码
     */
    private String obox_pwd;
    /**
     * obox里面的设备配置信息
     */
    private List<DeviceConfig> device_config;
    /**
     * obxo里面的场景设置信息
     */
    private List<CloudScene> cloudScene;


    public Obox(String obox_serial_id, String obox_version, String obox_name, String obox_pwd, List<DeviceConfig> device_config) {
        this.obox_serial_id = obox_serial_id;
        this.obox_version = obox_version;
        this.obox_name = obox_name;
        this.obox_pwd = obox_pwd;
        this.device_config = device_config;

    }

    public Obox() {

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

    public String getObox_name() {
        return obox_name;
    }

    public void setObox_name(String obox_name) {
        this.obox_name = obox_name;
    }

    public String getObox_pwd() {
        return obox_pwd;
    }

    public void setObox_pwd(String obox_pwd) {
        this.obox_pwd = obox_pwd;
    }

    public List<DeviceConfig> getDevice_config() {
        return device_config;
    }

    public void setDevice_config(List<DeviceConfig> device_config) {
        this.device_config = device_config;
    }

    public List<CloudScene> getCloudScene() {
        return cloudScene;
    }

    public void setCloudScene(List<CloudScene> cloudScene) {
        this.cloudScene = cloudScene;
    }

    public void setObox_serial_id(byte[] id) {
        this.obox_serial_id = Transformation.byteArryToHexString(id);
    }
    public void setObox_version(byte[] version) {
        this.obox_version = Transformation.byteArryToHexString(version);
    }
}
