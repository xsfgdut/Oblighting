package com.ob.obsmarthouse.common.bean;

/**ob系统播放音乐可接入设备接口
 * Created by adolf_dong on 2016/5/12.
 */
public interface MusicNode {
    int getNodeType();
    int getKind();
    String getId();
    byte[] getID();
    String getOboxName();
    boolean getIsInMusic();
    void setIsInMusic(boolean isInMusic);
}
