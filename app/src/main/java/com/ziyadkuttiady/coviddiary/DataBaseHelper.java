package com.ziyadkuttiady.coviddiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "CovidDiary.db";
    public final static String TABLE_NAME = "CovidDiary_Table";
    public final static String COL_1 = "ID";
    public final static String COL_2 = "START_DATE";
    public final static String COL_3 = "END_DATE";
    public final static String COL_4 = "START_TIME";
    public final static String COL_5 = "END_TIME";
    public final static String COL_6 = "START_PLACE";
    public final static String COL_7 = "END_PLACE";
    public final static String COL_8 = "PURPOSE";
    public final static String COL_9 = "DESCRIPTION";
    public final static String COL_10 = "VEHICLE_TYPE";
    public final static String COL_11 = "VEHICLE_CATEGORY";
    public final static String COL_12 = "VEHICLE_NUMBER";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " START_DATE TEXT," +
                " END_DATE TEXT, " +
                " START_TIME TEXT, " +
                " END_TIME TEXT, " +
                " START_PLACE TEXT, " +
                " END_PLACE TEXT, " +
                " PURPOSE TEXT, " +
                " DESCRIPTION TEXT, " +
                " VEHICLE_TYPE TEXT, " +
                " VEHICLE_CATEGORY TEXT, " +
                " VEHICLE_NUMBER TEXT)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String start_date,
                              String end_date,
                              String start_time,
                              String end_time,
                              String start_place,
                              String end_place,
                              String purpose,
                              String desc,
                              String vehicle_type,
                              String vehicle_category,
                              String vehicle_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, start_date);
        contentValues.put(COL_3, end_date);
        contentValues.put(COL_4, start_time);
        contentValues.put(COL_5, end_time);
        contentValues.put(COL_6, start_place);
        contentValues.put(COL_7, end_place);
        contentValues.put(COL_8, purpose);
        contentValues.put(COL_9, desc);
        contentValues.put(COL_10, vehicle_type);
        contentValues.put(COL_11, vehicle_category);
        contentValues.put(COL_12, vehicle_number);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(String id,
                              String start_date,
                              String end_date,
                              String start_time,
                              String end_time,
                              String start_place,
                              String end_place,
                              String purpose,
                              String desc,
                              String vehicle_type,
                              String vehicle_category,
                              String vehicle_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, start_date);
        contentValues.put(COL_3, end_date);
        contentValues.put(COL_4, start_time);
        contentValues.put(COL_5, end_time);
        contentValues.put(COL_6, start_place);
        contentValues.put(COL_7, end_place);
        contentValues.put(COL_8, purpose);
        contentValues.put(COL_9, desc);
        contentValues.put(COL_10, vehicle_type);
        contentValues.put(COL_11, vehicle_category);
        contentValues.put(COL_12, vehicle_number);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;

    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID='" + id + "'";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + "  ORDER BY " + COL_3 + " DESC;", null);
        return cursor;

    }
}
