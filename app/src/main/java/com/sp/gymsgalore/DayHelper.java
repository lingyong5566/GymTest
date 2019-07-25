package com.sp.gymsgalore;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DayHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dayLists.db";
    private static final int SCHEMA_VERSION = 1;

    private static final String TABLE_NAME = "day_table";
    private static final String COL1 = "_id";
    private static final String COL2 = "dayName";
    private static final String COL3  = "priAct";
    private static final String COL4  = "priReps";
    private static final String COL5 = "priTime";
    private static final String COL6  = "secAct";
    private static final String COL7  = "secReps";
    private static final String COL8 = "secTime";
    private static final String COL9 = "speechText";

    public DayHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Will be called once when the database is not created
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, dayName TEXT, priAct TEXT," +
                "priReps TEXT, priTime TEXT, secAct TEXT, secReps TEXT, secTime TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Will not be called until SCHEMA_VERSION increases
        // Here we can upgrade the database e.g. add more tables
    }

    public Cursor getAll() {
        return (getReadableDatabase().rawQuery(
                "SELECT _id, dayName, priAct, priReps,"+
                        "priTime, secAct, secReps, secTime FROM day_table ORDER BY dayName", null));
    }

    public Cursor getById(String id){
        String[] args = {id};
        return (getReadableDatabase().rawQuery(
                "SELECT * FROM day_table WHERE _ID = ?", args));
    }

    /*public Cursor getById(String id){
        String[] args = {id};
        return (getReadableDatabase().rawQuery(
                "SELECT _id, dayName, priAct, priReps, priTime, secAct, secReps, secTime, speechText" +
                        "FROM day_table WHERE _ID = ?", args));
    }*/ //Don't use this, for some reason despite selecting all columns from the table, it crashes.

    public void insert(String dayName, String priAct, String
            priReps, String priTime, String secAct, String secReps, String secTime) {
        ContentValues cv = new ContentValues();

        cv.put("dayName", dayName);
        cv.put("priAct", priAct);
        cv.put("priReps", priReps);
        cv.put("priTime", priTime);
        cv.put("secAct", secAct);
        cv.put("secReps", secReps);
        cv.put("secTime", secTime);

        getWritableDatabase().insert("day_table", "dayName", cv);
    }

    public void update (String id, String dayName, String priAct, String
            priReps, String priTime, String secAct, String secReps, String secTime){
        ContentValues cv = new ContentValues();
        String[] args = {id};

        cv.put("dayName", dayName);
        cv.put("priAct", priAct);
        cv.put("priReps", priReps);
        cv.put("priTime", priTime);
        cv.put("secAct", secAct);
        cv.put("secReps", secReps);
        cv.put("secTime", secTime);

        getWritableDatabase().update("day_table", cv, " _ID = ?", args);
    }

    public void delete(String id, String dayName, String priAct, String priReps, String priTime,
                       String secAct, String secReps, String secTime){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM day_table WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);

    }

    public String getID(Cursor c){
        return (c.getString(0));
    }

    public String getDayName(Cursor c) {
        return (c.getString(1));
    }

    public String getPriAct(Cursor c) {
        return (c.getString(2));
    }

    public String getPriReps(Cursor c) {
        return (c.getString(3));
    }

    public String getPriTime(Cursor c) {
        return (c.getString(4));
    }

    public String getSecAct(Cursor c) {
        return (c.getString(5));
    }

    public String getSecReps(Cursor c) {
        return (c.getString(6));
    }

    public String getSecTime(Cursor c) {
        return (c.getString(7));
    }

    public String getSpeechText(Cursor c){
        return (c.getString(8));
    }
}