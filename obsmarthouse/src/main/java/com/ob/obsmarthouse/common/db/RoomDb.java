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

import java.util.ArrayList;
import java.util.List;


/**
 * 记载对应obox序列号的房间信息(obox序列号唯一)
 * <p>
 * obox序列号 primkey(INTEGER)
 * 房间名 RomeName(TEXT)
 */

public class RoomDb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "obsmart.db";
    private static final int VERSION = 1;
    private String TABLE_NAME;
    private static final String PRIMARY_KEY = "_id";
    private static final String ROOM_NAME = "roomname";
    private static final String ROOM_IMG_DIR = "img_dir";
    private static final String NULL = "null";
    private SQLiteDatabase sqldb;

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    /**
     * 设定数据库当前的obox
     *
     * @param obox 选定的obox
     */
    public void setCunrentObox(Obox obox) {
        this.TABLE_NAME = obox.getObox_serial_id();
        createTable();
    }

    public RoomDb(Context context, String oboxSer) {
        this(context, DATABASE_NAME, null, VERSION);
        sqldb = getWritableDatabase();
        TABLE_NAME = oboxSer;
    }

    public RoomDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * 创建obox序列号为名称的表
     */
    private void createTable() {
        sqldb.execSQL("CREATE TABLE if not exists " + TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, img_dir TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * @param position
     */
    public void addRoom(Position position) {
        ContentValues cv = new ContentValues();
        cv.put(ROOM_NAME, position.getName());
        cv.put(ROOM_IMG_DIR, position.getImgDir());
        /*此处的null是在于当cv键集合为空的情况下，添加默认行，然而插入的值仍然为null*/
        sqldb.insert(TABLE_NAME, null, cv);
    }

    public void deleteRoom(Position position) {
        String[] args = new String[]{String.valueOf(position.getId())};
        sqldb.delete(TABLE_NAME, PRIMARY_KEY + " = ? ", args);
    }

    public void updateRoom(Position position) {
        String[] args = {String.valueOf(position.getId())};
        ContentValues cv = new ContentValues();
        cv.put(ROOM_NAME, position.getName());
        cv.put(ROOM_IMG_DIR, position.getImgDir());
        sqldb.update(TABLE_NAME, cv, PRIMARY_KEY + " = ? ",
                args);
    }

    /**
     * 查询当前obox中所有房间信息
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public List<Position> queryRooms() {
        List<Position> positions = new ArrayList<>();
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
                int id = cursor.getInt(cursor.getColumnIndex(PRIMARY_KEY));
                String name = cursor.getString(cursor.getColumnIndex(ROOM_NAME));
                String imgDir = cursor.getString(cursor.getColumnIndex(ROOM_IMG_DIR));
                positions.add(new Position(id, name, imgDir));
            }
            cursor.close();
        }
        return positions;
    }


}
