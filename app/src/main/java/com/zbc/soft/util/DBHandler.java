package com.zbc.soft.util;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {
	
	
	/**
	 * 启数据库
	 * */
	public SQLiteDatabase getDB(String path){
		SQLiteDatabase  db = SQLiteDatabase.openOrCreateDatabase(path+"/my.db3", null);
		return db;
	}
	/**
	 * 关闭数据
	 * */
	public void onDestroy(SQLiteDatabase db) {
		if (db != null && db.isOpen()) {
			db.close();
		}
	}
	
	public void createTable(SQLiteDatabase  db){
		
		db.execSQL("create table init_info (init_id integer primary key autoincrement,"
				+ " user_id varchar(50),"
				+ " user_nam varchar(50))");
	}
	
	public void deleteInit(SQLiteDatabase db, int id){
		
		db.execSQL("delete from init_info where init_id="+id);
		//db.delete("init_info", "init_id=?", new String[]{id+""});
	}
	public Map<String,Object> getInit(SQLiteDatabase db){
		Map<String,Object> map = null;
		try {
			Cursor cur = db.rawQuery("select * from init_info", null);
			while(cur.moveToNext()){
				map = new HashMap<String,Object>();
				map.put("init_id",cur.getInt(cur.getColumnIndex("init_id")));
				map.put("user_id",cur.getString(cur.getColumnIndex("user_id")));
				map.put("user_nam",cur.getString(cur.getColumnIndex("user_nam")));
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
        return map;
	}
	
	public void addInit(SQLiteDatabase db,Map<String,Object> map){
		
		db.execSQL("insert into init_info values(null , ? , ?  )", new String[] {
				map.get("user_id").toString(),map.get("user_nam").toString() });
	}
	public void updateInit(SQLiteDatabase db,Map<String,Object> map){
		
		ContentValues cv=new ContentValues();
        cv.put("user_id", map.get("user_id").toString());
        cv.put("user_name", map.get("user_nam").toString());
		db.update("init_info", cv, "init_id=?", new String[]{map.get("init_id").toString()});
	}
}
