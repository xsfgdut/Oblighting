package com.ob.obsmarthouse.common.bean;

import java.io.Serializable;

/**
 * 房间
 * Created by adolf_dong on 2016/5/6.
 */
public class Position implements Serializable{

    /**
     * 对应数据库主键
     */
    private int id;
    private String name;
    private String imgDir;
    private DevInRmHas devInRmHas = new DevInRmHas();

    public Position() {
    }

    public Position(int id, String name, String imgDir) {
        this.id = id;
        this.name = name;
        this.imgDir = imgDir;
    }

    public Position(String name, String imgDir) {
        this.imgDir = imgDir;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    public DevInRmHas getDevInRmHas() {
        return devInRmHas;
    }

    public void setDevInRmHas(DevInRmHas devInRmHas) {
        this.devInRmHas = devInRmHas;
    }

}
