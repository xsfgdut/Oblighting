package com.ob.obsmarthouse.common.constant;

/**
 * 网络请求参数
 * Created by adolf_dong on 2016/1/7.
 */
public interface CloudConstant {
    interface Source {
        String DEFAULT_PSW = "8888";
        String SERVER = "BDCloud.on-bright.com";
        //    String Common = "http://58.248.16.158/common";
//        String Common = "http://BDCloud.on-bright.com/common";
        String Common = "http://192.168.200.200/common";
    }

    /**
     * 返回状态，正常，失败，异常
     */
    interface ResponState {
        int State_OK = 0;
        int State_F = 1;
        int State_E = 2;
    }

    /**
     * 请求中的参数
     */
    interface ParameterKey {
        /**
         * 参数的key
         */
        String MSG = "msg";
        String USERNAME = "user_name";
        String ACCESS_TOKEN = "access_token";
        String PASS_WORD = "pwd";
        String OBOX = "obox";
        String WEIGHT = "weight";
        String OBOX_SERIAL_ID = "obox_serial_id";
        String OBOX_OLD_PWD = "obox_old_pwd";
        String OBOX_NEW_PWD = "obox_new_pwd";
        String OBOX_NEW_NAME = "obox_name";
        /**
         * 删除节点的时候使用，查询的时候如果是查询obox则不传该参数
         */
        String DEVICE_ID = "ID";
        /**
         * pa
         * 0代表查询obox，1代表查询设备
         */
        String QUERY_TYPE = "type";
        /**
         * 80代表组，00代表设备
         */
        String NODE_TYPE = "node_type";
        String OLD_ID = "old_id";
        String NEW_ID = "new_id";
        String ACTION = "action";
        String DEVICE_TYPE = "device_type";
        String DEVICE_CHILD_TYPE = "device_child_type";
        String STATE = "status";
        String TIME = "time";
        String OPERATE_TYPE = "operate_type";/*00是从当前组删除，01是添加到当前组,或者设置情景类型*/
        String SUPERID = "superID";
        String SCENE_ID = "scene_id";/*只有新增和修改场景名字的时候才需要传这参数*/
        String SCENE_NUMBER = "scene_number";/*没有场景之前该参数为0*/
        String SCENE_REMOTER_INDEX = "remoter_index";/*没有场景之前该参数为0*/
        String ACTION_TYPE = "action_type";/*场景类型enable，disable，只有新增和修改场景名字的时候才需要传这参数*/
        String SCENE_TYPE = "scene_type";/*0、无条件1、定时2、传感器*/
        String CONDITION_ID = "condition_id";
        String CONDITION = "condition";
        String ACTION_ID = "action_id";
        String FORCE = "force";
        String CONFIG = "config";
        /**
         * cmd指令
         */
        String CMD = "CMD";
        /**
         * 返回数据的key值
         */
        String IS_SUCCESS = "success";
        String START_INDEX = "start_index";
        String ADMIN_NAME = "admin_name";
        String GUEST_NAME = "guest_name";
        String COUNT = "count";
        String GROUPADDR = "groupAddr";
        String ADDR = "addr";
    }

    /**
     * 请求参数字段具体值
     */
    interface ParameterValue {
        String BILI = "ff";
        String LightDef = "01";
        String NULL = "null";
        String FORCE_TRUE = "true";
        String SIMPLE_SCENE = "0";
        String TIM_SCENE = "1";
        String SENSOR_SCENE = "2";
        String OBOXS = "oboxs";
        String SCENES = "scenes";
        String UPGRADES = "upgrades";
        String INT_SIMPLE_SCENE = "0";
        String INT_TIM_SCENE = "1";
        String INT_SENSOR_SCENE = "2";
        String IS_DEL_MEMBER = "0";
        String IS_ADD_MEMBER = "1";
        int MSC_IS_GROUP = 1;
        int MSC_IS_SINGLE = 0;
        /**
         * 删除情景
         */
        String DELETE_SCENE = "delete";
        /**
         * 执行情景
         */
        String EXECUTE_SCENE = "execute";
        /**
         * 重命名情景
         */
        String RENAME_SCENE = "rename";
        /**
         * 修改情景
         */
        String MODIFY_SCENE = "modify";
        /**
         * 使能情景
         */
        String ENABLE_SCENE = "1";
        /**
         * 不使能情景
         */
        String DISABLE_SCENE = "0";
        /**
         * 添加行为节点到情景内
         */
        String ADD_ACTION = "add";
        /**
         * 修改情景内节点状态,ps 因为本地情景也是不支持情景内节点修改
         */
        String MODIFY_ACTION = "add";
        /**
         * 从情景内删除行为节点
         */
        String DEL_ACTION = "delete";
        String SETTING_ALL_NODE_STATUS = "setting_all_node_status";
        /**
         * 修改条件
         */

        String UPDATE_CONDITION = "update";
        /**
         * 新增加条件
         */
        String CREAT_CONDITION = "add";
        /**
         * 删除条件,条件是传感器这些东
         */
        String DEL_CONDITION = "delete";
        /**
         * 创建情景
         */
        String CREAT_SCENE = "add";
    }

    /**
     * cmd的对应值
     */
    interface CmdValue {

        /**
         * 为了把删除组方法从组操作方法中独立出来新添加的字段，不用于服务器交互中的字段
         */
        String DEL_GROUP = "del_group";
        String REGISTER = "register";
        String LOGIN = "login";
        String ADD_OBOX = "add_obox";
        String DELETE_OBOX = "delete_obox";
        String UPDATE_OBOX_PASSWORD = "update_obox_pwd";
        String RESET_OBOXPWD = "reset_obox_pwd";
        String UPDATE_OBOX_NAME = "update_obox_name";
        String RELEASE_ALL_DEVICES = "release_all_devices";
        String DELETE_SINGLE_DEVICE = "del_single_device";
        String QUERY_VERSION = "query_version";
        String UPDATE_NODE_NAME = "update_node_name";/*修改组/节点名字*/
        String SETTING_NODE_STATUS = "setting_node_status";
        String OPERATE_GROUP_MEMBERS = "operate_group_members";
        String SETTING_SC_ID = "setting_sc_id";
        String SETTING_SC_CONDITION = "setting_sc_condition";/*设置场景序号的条件信息*/
        String SETTING_SC_ACTION = "setting_sc_action";
        String QUERY_SCENES = "query_scenes";
        String QUERY_UPGRADES = "query_upgrades";
        String QUERY_OBOX_BIND = "query_obox_bind";
        String QUERY_DEVICE = "query_device";
    }


    /**
     * 设备类型
     */
    interface NodeType {

        //只使用于云版本
        String IS_GROUP = "80";
        String IS_SINGLE = "00";
        String IS_LAMP = "1";
        String IS_SIMPLE = "01";
        String IS_COOL = "02";
        String IS_COLOUR = "03";
        String IS_SENSOR = "11";
        String IS_ALS_SENSOR = "1";
        String IS_WATER_SENSOR = "2";
        String IS_RADAR_SENSOR = "3";
        String IS_CO_SENSOR = "4";
    }

    /**
     * activity intent传输的key
     * handler message.what值
     */
    interface TransKey {
        /**
         * 传递键，情景选灯后传递到参数设置的页面，单节点
         */
        String CHOOSED_SGLNODE = "choosed_sglnode";

        /**
         * 传递键，情景选灯后传递到参数设置的页面，组节点
         */
        String CHOOSED_GROUPNODE = "choosed_groupnode";

        /**
         * 传递键，情景选灯后传递到参数设置的页面，组内的单节点
         */
        String CHOOSED_SIGNODE = "choosed_signode";


        /**
         * 键，新建情景的时候选好的行为列表
         */
        String ACTION_FC = "scene_fc";
        /**
         * 传递键，用于查看场景详细，与创建场景选完节点后传递action信息
         */
        String CLD_SCENACTS = "CLSenACS";
        /**
         * 传递键，用于开始创建场景时过滤灯类型节点后传递给选灯界面的DCforCtrl列表
         */
        String CLD_SCENDCFCS = "CLSenDCFCS";
        /**
         * 传递键，用于开始创建场景时过滤组类型节点后传递给选灯界面的DCGrouforCtrl列表
         */
        String CLD_SCENDGCFCS = "CLSenDGFCS";
        /**
         * 键，传递SceneFC
         */
        String CLD_SCENEFC = "sceneFC";
        /**
         * 新建位置
         */
        int CREAT_POSITON = 1;
    }

    /**
     * activityforResult传递键
     */
    interface ResultKey {
        /**
         * forresult键，选择节点
         */
        int CHOOSE_ACTION = 0;
        /**
         * forresult键,设置行为状态
         */
        int ACTION_STATUS = 1;
        /**
         * forresult键,设置情景条件   传感器 一般 定时
         */
        int SET_CONDITION = 2;
        /**
         * forresult键,点击情景详情的任何修改
         */
        int EVEN_MODIFY_SCENE = 3;
        String KEY = "json";
    }
    /**
     * {@link #ADMIN}与的主要区别在于其可以创建楼层概念，楼宇概念可以用来区分智能楼宇和智能家居
     * 服务器登录后详细的用户类型
     */
    interface CloudDitalMode {
        /**
         * 超级管理员，拥有所有权限
         */
        String SUPERROOT = "00";
        /**
         * 总管理员，拥有创建楼宇管理员权限，次于超级管理员的所有权限，当有授权码的时候就可以创建ADMINOFBD
         */
        String ROOT = "01";
        /**
         * 楼宇管理员，权限:创建guest，创建楼层，创建房间，增加位置信息，修改组节点关系，创建情景,设备升级权限，
         */
        String ADMIN = "02";

        /**
         * 访客，拥有节点控制，组控制，情景使能，位置信息读取权限
         */
        String GUEST = "03";
    }
}