package com.ziyadkuttiady.coviddiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME ="CovidDiary.db";
    public final static String TABLE_NAME ="CovidDiary_Table";
    public final static String COL_1 ="ID";
    public final static String COL_2 ="V_DATE";
    public final static String COL_3 ="START_TIME";
    public final static String COL_4 ="END_TIME";
    public final static String COL_5 ="PLACE";
    public final static String COL_6 ="PURPOSE";
    public final static String COL_7 ="DESCRIPTION";

    public DataBaseHelper(Context context) {
        super( context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS "
                +TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " V_DATE TEXT," +
                " START_TIME TEXT, " +
                " END_TIME TEXT, " +
                " PLACE TEXT, " +
                " PURPOSE TEXT, " +
                " DESCRIPTION TEXT)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL( "DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate( db );
    }
    public boolean insertData(String v_date,String st_time, String end_time, String place,String purpose, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put( COL_2,v_date );
        contentValues.put( COL_3,st_time );
        contentValues.put( COL_4,end_time );
        contentValues.put( COL_5,place );
        contentValues.put( COL_6,purpose );
        contentValues.put( COL_7,desc );

        long result = db.insert( TABLE_NAME, null,contentValues );
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updateData(String id, String v_date,String st_time, String end_time, String place,String purpose, String desc){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1,id );
        contentValues.put( COL_2,v_date );
        contentValues.put( COL_3,st_time );
        contentValues.put( COL_4,end_time );
        contentValues.put( COL_5,place );
        contentValues.put( COL_6,purpose );
        contentValues.put( COL_7,desc );

        db.update( TABLE_NAME, contentValues,"ID=?", new String[]{id} );
        return true;

    }

    public Cursor getData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME +" WHERE ID='"+id+"'";
        Cursor cursor = db.rawQuery( query,null );

        return cursor;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete( TABLE_NAME,"ID=?", new String[]{id} );
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" ;",null );
        return cursor;

    }
}
