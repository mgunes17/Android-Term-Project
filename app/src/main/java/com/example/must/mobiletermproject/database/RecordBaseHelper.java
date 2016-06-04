package com.example.must.mobiletermproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by must on 03.06.2016.
 */
public class RecordBaseHelper extends SQLiteOpenHelper {
    private static int VERSION = 1;
    private static final String DATABASE_NAME = "recordBase.db";

    public RecordBaseHelper(Context c){
        super(c, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("create table record (" +
                "time text primary key, " +
                "operatorName text, " +
                "strength int, " +
                "latitude text, " +
                "longitude text);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public long insert(Record record){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RecordDBSchema.RecordTable.Cols.TIME, String.valueOf(record.getZaman()));
        values.put(RecordDBSchema.RecordTable.Cols.OPERATOR_NAME, record.getOperatorAdi());
        values.put(RecordDBSchema.RecordTable.Cols.STRENGTH, record.getSinyalGucu());
        values.put(RecordDBSchema.RecordTable.Cols.LATITUTE, record.getEnlem());
        values.put(RecordDBSchema.RecordTable.Cols.LONGITUTE, record.getBoylam());

        long r = db.insert(RecordDBSchema.RecordTable.name, null, values);
        db.close();

        return r;
    }

    public List<Record> read(){
        String query = "select * from record";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<Record> list = new ArrayList<Record>();

        if (cursor.moveToFirst()) {
            do {
                Record r = new Record();
                //r.setZaman(new Date(cursor.getString(0)));
                r.setOperatorAdi(cursor.getString(1));
                r.setSinyalGucu(Integer.valueOf(cursor.getString(2)));
                r.setEnlem(Double.valueOf(cursor.getString(3)));
                r.setBoylam(Double.valueOf(cursor.getString(4)));

                list.add(r);
            } while (cursor.moveToNext());
        }

        return list;
    }
}
