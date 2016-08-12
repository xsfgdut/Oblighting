package com.ob.obsmarthouse.common.bean;

/**
 * 登陆者
 * Created by adolf_dong on 2016/8/12.
 */
public class User {
    private static User user;
    private String rootName;
    private String adminName;
    private String superRootName;
    private String guestName;

    public static User getUser() {
        synchronized (User.class) {
            if (user == null) {
                user = new User();
            }
            return user;
        }
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getSuperRootName() {
        return superRootName;
    }

    public void setSuperRootName(String superRootName) {
        this.superRootName = superRootName;
    }
}
