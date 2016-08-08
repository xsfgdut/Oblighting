package com.ob.obsmarthouse.common.bean.cloudbean;

import java.util.List;

/**
 * 本来可以让组和节点继承同一个接口  后续有需要就做
 * 组装生成的组
 * Created by adolf_dong on 2016/1/21.
 */
@Deprecated
public class CloudGroup {

    /**
     * 组id
     */
    private String Id;

    /**
     * 组id相同的DeviceConfig列表，组内的节点
     */
    private List<DeviceConfig> deviceConfigs;

    /**
     * 设备类型
     */
    private String device_type;
    /**
     * 设备子类型
     */
    private String device_child_type;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public List<DeviceConfig> getDeviceConfigs() {
        return deviceConfigs;
    }

    public void setDeviceConfigs(List<DeviceConfig> deviceConfigs) {
        this.deviceConfigs = deviceConfigs;
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
}
