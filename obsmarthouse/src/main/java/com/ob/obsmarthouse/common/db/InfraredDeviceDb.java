package com.ob.obsmarthouse.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ob.obsmarthouse.common.bean.Position;
import com.ob.obsmarthouse.common.bean.cloudbean.Obox;
import com.ob.obsmarthouse.common.bean.localbean.EquipmentInfra;
import com.ob.obsmarthouse.common.bean.localbean.ObScene;
// FIXME: 2016/7/7 没完成
/**
 * 红外遥控内绑定设备数据库，以红外遥控设备序列号唯一标示键
 * Created by adolf_dong on 2016/7/4.
 */
public class InfraredDeviceDb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "obsmart.db";
    private String TABLE_NAME;
    private String id = "_id";
    private String type = "type";
    private String sectype = "sectype";
    private String devName = "name";
    private SQLiteDatabase sqldb;
    private static final int VERSION = 1;

    public InfraredDeviceDb(Context context, String tableName) {
        this(context, DATABASE_NAME, null, VERSION);
        sqldb = getWritableDatabase();
        this.TABLE_NAME = tableName;
    }

    public void setCurrentRoom(Obox obox,Position position) {
        this.TABLE_NAME = obox.getObox_serial_id()+ position.getId();
        createTable();
    }

    public InfraredDeviceDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private void createTable() {
        sqldb.execSQL("CREATE TABLE if not exists " + TABLE_NAME +
                " (" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, device_ser TEXT, scene_ser INTEGER);");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addScene(ObScene obScene) {
        ContentValues cv = new ContentValues();
        cv.put(type, obScene.getSerisNum());
        sqldb.insert(TABLE_NAME, null, cv);
    }

    /**
     * @param equipmentInfra
     */
    public void deleteDevice(EquipmentInfra equipmentInfra) {
        String[] args = new String[]{String.valueOf(equipmentInfra.getId())};
        sqldb.delete(TABLE_NAME, type + " = ? ", args);
    }


    public void addNode(EquipmentInfra equipmentInfra) {
        ContentValues cv = new ContentValues();
        cv.put(type, String.valueOf(equipmentInfra.getType()));
        cv.put(sectype, String.valueOf(equipmentInfra.getScdType()));
        cv.put(devName, equipmentInfra.getScdType());
        sqldb.insert(TABLE_NAME, null, cv);
    }
}
