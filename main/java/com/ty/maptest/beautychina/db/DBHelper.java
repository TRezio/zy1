package com.ty.maptest.beautychina.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类，用来创建数据库和表格，集成对数据库的增删改查
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "beautyChinaDB";//数据库名称
    private final static int VERSION = 1;

    //数据库字段名称，定义为常量，方便使用
    public final static String TABLE_NAME = "place";
    public final static String FIELD_ID = "_id";
    public final static String FIELD_NAME = "name";
    public final static String FIELD_DESCRIPTION = "description";
    public final static String FIELD_DETAILS = "details";


    String sql1 = "create table if not exists "+ TABLE_NAME+"(" +
            "_id integer primary key autoincrement , " +
            "name text," +
            "description text," +
            "details text"+
            ");"; // 创建 课程 表格


    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override // 创建数据库
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL("drop table if exists "+TABLE_NAME);
          db.execSQL(sql1);
    }

    //下面是基础数据库相关操作，增删改查

    /**
     * 插入信息到数据库中
     * @param bean
     */
    public void add(PlaceInfoBean bean)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAME,bean.getName());
        contentValues.put(FIELD_DESCRIPTION,bean.getDescription());
        contentValues.put(FIELD_DETAILS,bean.getDetail());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }
    /**
     * 删除信息
     * @param id
     */
    public void delete(int id)
    {
        String sql= "_id=?";
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, sql, new String[] {""+id});
        db.close();
    }
    /**
     * 更改信息
     * @param bean
     */
    public void update(PlaceInfoBean bean)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAME,bean.getName());
        contentValues.put(FIELD_DESCRIPTION,bean.getDescription());
        contentValues.put(FIELD_DETAILS,bean.getDetail());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, contentValues, "_id=?", new String[] {""+bean.getId()});
        db.close();
    }
    /**
     * 查询所有
     * @return 所有信息列表
     */
    public List<PlaceInfoBean> queryAll()
    {
        List<PlaceInfoBean> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null,null, null,null,null,null,null);
            if(cursor != null && cursor.getCount() > 0)
            {
                while(cursor.moveToNext())
                {
                    PlaceInfoBean bean = new PlaceInfoBean();
                    bean.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
                    bean.setName(cursor.getString(cursor.getColumnIndex(FIELD_NAME)));
                    bean.setDescription(cursor.getString(cursor.getColumnIndex(FIELD_DESCRIPTION)));
                    bean.setDetail(cursor.getString(cursor.getColumnIndex(FIELD_DETAILS)));
                    data.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(cursor != null)
            cursor.close();
        db.close();
        return data;
    }
    /**
     * 按id查询信息
     * @param  fieldName
     * @param value
     * @return
     */
    public List<PlaceInfoBean> rawQuery(String fieldName,String value)
    {
        List<PlaceInfoBean> datas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from "+TABLE_NAME+" where "+fieldName+"=?",new String[] {value});
            if(cursor != null && cursor.getCount() > 0)
            {
                while(cursor.moveToNext())
                {
                    PlaceInfoBean bean = new PlaceInfoBean();
                    bean.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
                    bean.setName(cursor.getString(cursor.getColumnIndex(FIELD_NAME)));
                    bean.setDescription(cursor.getString(cursor.getColumnIndex(FIELD_DESCRIPTION)));
                    bean.setDetail(cursor.getString(cursor.getColumnIndex(FIELD_DETAILS)));
                    datas.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        if(cursor != null)
            cursor.close();
        return datas;
    }

    public List<PlaceInfoBean> rawQueryKeyWork(String fieldName,String keyword)
    {
        List<PlaceInfoBean> datas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from "+TABLE_NAME+" where "+fieldName+" like ?",new String[] {"%"+keyword+"%"});
            if(cursor != null && cursor.getCount() > 0)
            {
                while(cursor.moveToNext())
                {
                    PlaceInfoBean bean = new PlaceInfoBean();
                    bean.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
                    bean.setName(cursor.getString(cursor.getColumnIndex(FIELD_NAME)));
                    bean.setDescription(cursor.getString(cursor.getColumnIndex(FIELD_DESCRIPTION)));
                    bean.setDetail(cursor.getString(cursor.getColumnIndex(FIELD_DETAILS)));
                    datas.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        if(cursor != null)
            cursor.close();
        return datas;
    }



}
