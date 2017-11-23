package com.example.sunny.companiesproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sunny on 22/11/2017.
 */

public class CompanyDBHelper extends SQLiteOpenHelper {

    protected static final String TABLE_NAME = "companies";
    protected static final String COL_ID = "id";
    protected static final String COL_NAME = "name";
    protected static final String COL_ADDRESS = "address";
    protected static final String COL_IMAGE = "image";

    public CompanyDBHelper(Context context) {
        super(context, "companies.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, COL_ID, COL_NAME, COL_ADDRESS, COL_IMAGE);

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
