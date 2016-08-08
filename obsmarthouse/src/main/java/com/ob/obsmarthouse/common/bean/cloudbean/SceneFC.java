package com.ob.obsmarthouse.common.bean.cloudbean;

import java.io.Serializable;

/**Scene的包装类，将其用于和obox绑定
 * Created by adolf_dong on 2016/2/3.
 */
@Deprecated
public class SceneFC implements Serializable{
    private Obox obox;

    public CloudScene getCloudScene() {
        return cloudScene;
    }

    public void setCloudScene(CloudScene cloudScene) {
        this.cloudScene = cloudScene;
    }

    public Obox getObox() {
        return obox;
    }

    public void setObox(Obox obox) {
        this.obox = obox;
    }

    private CloudScene cloudScene;

    public SceneFC(Obox obox, CloudScene cloudScene) {
        this.obox = obox;
        this.cloudScene = cloudScene;
    }

    public  SceneFC(){

    }
}
