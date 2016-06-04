package com.example.must.mobiletermproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by must on 03.06.2016.
 */
public class RecordLab {
    private static RecordLab recordLab;
    private List<Record> mRecords;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private RecordLab(Context c){
        mContext = c.getApplicationContext();
        mDatabase = new RecordBaseHelper(mContext).getWritableDatabase();
        mRecords = new ArrayList<>();
    }
}
