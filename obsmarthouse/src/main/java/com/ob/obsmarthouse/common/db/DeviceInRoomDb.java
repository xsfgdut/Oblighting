package com.ob.obsmarthouse.common.db;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.ob.obsmarthouse.common.bean.Position;
import com.ob.obsmarthouse.common.bean.cloudbean.Obox;
import com.ob.obsmarthouse.common.bean.localbean.ObNode;
import com.ob.obsmarthouse.common.bean.localbean.ObScene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 房间内的设备信息数据，表名称以obox序列号+房间的主键index为唯一标识
 * Created by adolf_dong on 2016/7/4.
 */
public class DeviceInRoomDb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "obsmart.db";
    private String TABLE_NAME;
    private String primary = "_id";
    private String sceneSer = "scene_ser";
    private String deviceSer = "device_ser";
    private SQLiteDatabase sqldb;
    private static final int VERSION = 1;

    public DeviceInRoomDb(Context context, String tableName) {
        this(context, DATABASE_NAME, null, VERSION);
        sqldb = getWritableDatabase();
        this.TABLE_NAME = tableName;
    }

    /**
     * 设置当前指向obox和房间号，如果该房间号并无数据库，则创建数据库
     * 在点击房间进入下一页面实例化数据的时候调用
     *
     * @param obox
     * @param position
     */
    public void setCurrentRoom(Obox obox, Position position) {
        this.TABLE_NAME = obox.getObox_serial_id() + position.getId();
        createTable();
    }

    public DeviceInRoomDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private void createTable() {
        sqldb.execSQL("CREATE TABLE if not exists " + TABLE_NAME +
                " (" + primary + " INTEGER PRIMARY KEY AUTOINCREMENT, device_ser TEXT, scene_ser INTEGER);");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 存入情景序列号，取出时拿序列号从本地情景数据源中获得实例
     *
     * @param obScene
     */
    public void addScene(ObScene obScene) {
        ContentValues cv = new ContentValues();
        cv.put(sceneSer, obScene.getSerisNum());
        sqldb.insert(TABLE_NAME, null, cv);
    }

    /**
     * 删除房间内情景
     *
     * @param obScene 删除的目标情景
     */
    public void deleteScene(ObScene obScene) {
        String[] args = new String[]{String.valueOf(obScene.getSerisNum())};
        sqldb.delete(TABLE_NAME, sceneSer + " = ? ", args);
    }

    /**
     * 添加房间内节点，
     *
     * @param obNode
     */
    public void addNode(ObNode obNode) {
        ContentValues cv = new ContentValues();
        // FIXME: 2016/7/8 如果键值不匹配则可能此处需要修改为new String（"utf-8"）
        cv.put(deviceSer, Arrays.toString(obNode.getSerNum()));
        sqldb.insert(TABLE_NAME, null, cv);
    }

    /**
     * 删除房间内节点
     *
     * @param obNode 删除的目标节点
     */
    public void deleteNode(ObNode obNode) {
        String[] args = new String[]{Arrays.toString(obNode.getSerNum())};
        sqldb.delete(TABLE_NAME, deviceSer + " = ? ", args);
    }

    /**
     * 查询当前房间中的节点数据
     * 取出时拿序列号从本地节点数据源中获得实例
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public List<ObNode> queryNodes() {
        List<ObNode> obNodes = new ArrayList<>();
        Cursor cursor;
        int apiLevel = android.os.Build.VERSION.SDK_INT;
        /*高于JELLY_BEAN版本用执行原生sql语句查询，否则执行query*/
        if (apiLevel > Build.VERSION_CODES.JELLY_BEAN) {
            cursor = sqldb.rawQuery("select * from " + TABLE_NAME, null, null);
        } else {
            cursor = sqldb.query(TABLE_NAME, null, null, null, null, null, null);
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String serNum = cursor.getString(cursor.getColumnIndex(deviceSer));
                if (serNum == null || serNum.equals("null")) {
                    continue;
                }
                ObNode obNode = new ObNode();
                obNode.setSerNum(serNum.getBytes());
                obNodes.add(obNode);
            }

            cursor.close();
        }
        return obNodes;
    }

    /**
     * 查询当前房间中的情景数据
     * 取出时拿序列号从本地情景数据源中获得实例
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public List<ObScene> queryScenes() {
        List<ObScene> obNodes = new ArrayList<>();
        Cursor cursor;
        int apiLevel = android.os.Build.VERSION.SDK_INT;
        /*高于JELLY_BEAN版本用执行原生sql语句查询，否则执行query*/
        if (apiLevel > Build.VERSION_CODES.JELLY_BEAN) {
            cursor = sqldb.rawQuery("select * from " + TABLE_NAME, null, null);
        } else {
            cursor = sqldb.query(TABLE_NAME, null, null, null, null, null, null);
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int sceneSerNum = cursor.getInt(cursor.getColumnIndex(sceneSer));
                if (sceneSerNum == 0) {
                    continue;
                }
                ObScene obScene = new ObScene();
                obScene.setSerisNum(sceneSerNum);
                obNodes.add(obScene);
            }
            cursor.close();
        }
        return obNodes;
    }
}
