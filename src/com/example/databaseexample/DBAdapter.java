package com.example.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	DBHelper helper;
	public DBAdapter(Context context) {
		Log.w("TAG", "BDAdapter construct");
		helper = new DBHelper(context);
	}
	
	public long insertData(String name, String amount) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(DBHelper.NAME, name);
		cv.put(DBHelper.AMOUNT, amount);
		return db.insert(DBHelper.TABLE_NAME, null, cv);
	}
	
	public String getAllData() {
		SQLiteDatabase db = helper.getWritableDatabase(); 
		String[] columns = DBHelper.ALL_KEYS;
		String orderBy =  DBHelper.NAME + " ASC ";
		Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, null, null, null, null, orderBy);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext()) {
			int cid = cursor.getInt(0);
			String name = cursor.getString(1);
			String amount = cursor.getString(2);
			buffer.append(cid + " " + name + " " + amount + "\n");
		}
		return buffer.toString();
	}
	
	
	public String getData(String name) {
		SQLiteDatabase db = helper.getWritableDatabase(); 
		String[] columns = {DBHelper.NAME, DBHelper.AMOUNT};
		String[] selectionArgs = {name};
		Cursor cursor = db.query(DBHelper.TABLE_NAME, 
			columns, DBHelper.NAME + " =?", selectionArgs, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext()) {
			int nameIndex = cursor.getColumnIndex(DBHelper.NAME);
			int amountIndex = cursor.getColumnIndex(DBHelper.AMOUNT);
			String personName = cursor.getString(nameIndex);
			String amount = cursor.getString(amountIndex);
			
			buffer.append(personName + " " + amount + "\n");
		}
		return buffer.toString();
	}
	
	public int updateRow(String oldName, String newName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBHelper.NAME, newName);
		String[] whereArgs = {oldName};
		int rowsUpdated = db.update(DBHelper.TABLE_NAME, contentValues, DBHelper.NAME+" =? " , whereArgs);
		return rowsUpdated;
	}

	public int updateAmount(String nameToUpdate,String newAmount) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBHelper.AMOUNT, newAmount);
		String[] whereArgs = {nameToUpdate};
		int rowsUpdated = db.update(DBHelper.TABLE_NAME, contentValues, DBHelper.NAME+" =? " , whereArgs);
		return rowsUpdated;
	}
	
	public int updateName(String oldName, String newName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBHelper.NAME, newName);
		String[] whereArgs = {oldName};
		int rowsUpdated = db.update(DBHelper.TABLE_NAME, contentValues, DBHelper.NAME+" =? " , whereArgs);
		return rowsUpdated;
	}
	
	public int deleteRow(String name) {
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String[] whereArgs = {name};
		int count = db.delete(DBHelper.TABLE_NAME, DBHelper.NAME+" =? ", whereArgs);
		return count;
	}
	
	static class DBHelper extends SQLiteOpenHelper {
	
		private static final String DATABASE_NAME = "databaseexample.db";
		private static final String TABLE_NAME = "debts";
		private static final String UID = "_id";
		private static final String NAME = "name";
		private static final String AMOUNT = "amount";
		private static final String[] ALL_KEYS= new String[] {UID, NAME, AMOUNT};
		private static final int DATABASE_VERSION = 5;
		private static final String CREATE_TABLE = "Create table " 
			+ TABLE_NAME 
			+ " (" + UID + " integer primary key autoincrement, " 
			+ NAME + " varchar(255), " 
			+ AMOUNT +" varchar(255));";
		
		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE);
				Log.w("TAG", "Upgrading application's database from version ");
			} catch (SQLException e) {
				 e.printStackTrace();
			}
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				Log.w("TAG", "Upgrading application's database from version " + oldVersion
						+ " to " + newVersion + ", which will destroy all old data!");
				db.execSQL("drop table if exists " + TABLE_NAME);
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
