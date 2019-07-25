package com.sp.gymsgalore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notelist.db";
    private static final int SCHEMA_VERSION = 1;
    private static final String COL1 = "_id";

    public NoteHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Will be called once when the database is not created
        db.execSQL("CREATE TABLE notes_table ( _id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", notes TEXT, daygroup TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Will not be called until SCHEMA_VERSION increases
        // Here we can upgrade the database e.g. add more tables
    }

    public Cursor getAll3() {
        return (getReadableDatabase().rawQuery(
                "SELECT _id, notes, daygroup FROM notes_table" , null));
    }

    public Cursor getById3(String id) {
        String[] args = {id};
        return (getReadableDatabase().rawQuery(
                "SELECT _id, notes, daygroup FROM notes_table WHERE _ID = ?", args));
    }

    public void insert3(String notes, String daygroup) {
        ContentValues cv = new ContentValues();

        cv.put("notes", notes);
        cv.put("daygroup", daygroup);

        getWritableDatabase().insert("notes_table", "notes", cv);
    }

    public void update3(String id, String notes, String daygroup) {
        ContentValues cv = new ContentValues();
        String[] args = {id};

        cv.put("notes", notes);
        cv.put("daygroup", daygroup);

        getWritableDatabase().update("notes_table", cv, " _ID = ?", args);
    }

    public void delete3(String id, String notes, String daygroup){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM notes_table WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);

    }

    public String getID2(Cursor c) {
        return (c.getString(0));
    }

    public String getNotes(Cursor c) {
        return (c.getString(1));
    }

    public String getDayGroup(Cursor c) {
        return (c.getString(2));
    }
}
