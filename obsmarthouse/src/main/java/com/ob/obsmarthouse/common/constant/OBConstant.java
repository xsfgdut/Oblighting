package com.ob.obsmarthouse.common.constant;

public interface OBConstant {

    /**
     * 键值
     */
    interface StringKey {
        String UTF8 = "utf-8";
        String PSW = "88888888";
        String KEY = "obkeydata";
        String RFPSWKEY = "rfpsw";
        String SPKEY = "psw";
        String ISLOCALKEY = "islocal";
        String CLOUD_NAME = "cloudname";
        String CLOUD_PSW = "cloudpsw";
        String JSON_OBOX = "cloud_obox";
        String NODETYPE = "nodetype";
        String CTRL_NODE = "ctrl_node";
        String LAMP = "lamp";
    }

    /**
     * {@link #ADMIN}与的主要区别在于其可以创建楼层概念，楼宇概念可以用来区分智能楼宇和智能家居
     * 服务器登录后详细的用户类型
     */
    interface CloudDitalMode {
        /**
         * 超级管理员，拥有所有权限
         */
        int SUPERROOT = 0;
        /**
         * 总管理员，拥有创建楼宇管理员权限，次于超级管理员的所有权限，当有授权码的时候就可以创建ADMINOFBD
         */
        int ROOT = 1;
        /**
         * 楼宇管理员，权限:创建guest，创建楼层，创建房间，增加位置信息，修改组节点关系，创建情景,设备升级权限，
         */
        int ADMIN = 2;

        /**
         * 访客，拥有节点控制，组控制，情景使能，位置信息读取权限
         */
        int GUEST = 3;
    }

    /**
     * 回复类型
     */
    interface ReplyType {
        /**
         * 失败
         */
        int FAL = 0;
        /**
         * 成功
         */
        int SUC = 1;

        int BURN_BACK = 100;
        int UP_BACK = 101;
        int WIPE_BACK = 102;
        int PROTECT_BACK = 103;

        /**
         * 获取obox名称的返回
         */
        int GET_OBOX_NAME_BACK = 1;
        /**
         * 拿单灯返回，表示obox中还有灯，可将拿灯方法非组id参数累加1继续拿灯
         */
        int GET_SINGLENODE_BACK = 2;
        /**
         * 拿组返回
         */
        int GET_GROUP_BACK = 3;
        int CLOSE_CLOUD_FAL = 4;
        int GET_STATE = 5;
        int CHANG_RF_PSW_FAL = 6;
        int CHANG_RF_PSW_SUC = 7;
        int ON_SET_MODE = 8;
        int GET_OBOX_MSG_BACK = 9;
        int OPEN_CLOUD_FAL = 10;
        int CLOSE_CLOUD_SUC = 11;
        int OPEN_CLOUD_SUC = 12;
        int UPLOAD_CLOUD = 13;
        int GET_SCENE_BACK = 14;
        int SEARCH_SUC = 15;
        int SEARCH_FAL = 15;
        int FORCE_SEARCH_SUC = 16;
        int FORCE_SEARCH_FAL = 17;
        int STOP_SEARCH_SUC = 18;
        int STOP_SEARCH_FAL = 19;
        int SEARCH_NODE_FAL = 20;
        int SEARCH_NODE_SUC = 21;
    }

    /**
     * 网络状态
     */
    interface NetState {
        /**
         * ap 状态
         */
        int ON_AP = 1;
        /**
         * station状态
         */
        int ON_STATION = 2;

        int ON_CLOUD = 3;
        /**
         * 无网络状态
         */
        int UN_NET = 0;
        /**
         * 广播包接收完毕
         */
        int ON_DSFINISH = 1;
    }

    /**
     * 加密类型
     */
    interface PackType {
        int ORIGINAL_ENCRYPTED = 0;
        int ACTIVATED_UNENCRYPTED = 1;
        int UNENCRYPTED = 2;
    }


    /**
     * 出错类型
     */
    interface ErrType {
        int CRC_ERR = 1;
        int TIMEOUT = 2;
        int SUPPORT_ERR = 3;
        int PSW_ERR = 4;
        int FLASH_ERR = 5;
    }


    /**
     * 设备类型和子类型
     */
    interface NodeType {
        int IS_LAMP = 1;//主设备类型是灯
        int IS_SIMPLE_LAMP = 1;//单色灯
        int IS_WARM_LAMP = 2;//双色灯
        int IS_COLOR_LAMP = 3;//三色灯

        int IS_COOKER = 2;
        /**
         * 加湿器
         */
        int IS_HUMIDIFIER = 3;
        int IS_OBSOCKET = 4;
        int IS_CURTAIN = 5;
        int WINDOW_CURTAINS = 1;
        int THE_CURTAINS = 2;
        int IS_FAN = 6;
        int IS_AIR_CLEAN = 7;
        int IS_TV = 8;
        /**
         * 传感器与子类型
         */
        int IS_SENSOR = 11;
        int ALS = 1;
        int FLOOD = 2;
        int RADAR = 3;
        int CO = 4;
        int ENVIRONMENTAL = 5;
        int BODY = 6;
        int PM2_5 = 7;
        int POWER_CHECK = 8;
        int AMMETER = 12;
        int AIR_CON = 13;
        /**
         * id长度
         */
        int ID_LEN = 16;
    }

    /**
     * 属性动画proper
     */
    interface AnimatorProper {
        String ROTATE = "rotation";
        String TRANSX = "translationX";
        String TRANSY = "translationY";
        String SCALEX = "scaleX";
        String SCALEY = "scaleY";
        String ALPHA = "alpha";
    }

}