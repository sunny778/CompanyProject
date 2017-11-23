package com.example.sunny.companiesproject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Sunny on 22/11/2017.
 */

public class CompanyProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.sunny.companiesproject.authority.companies";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CompanyDBHelper.TABLE_NAME);

    private CompanyDBHelper helper;

    @Override
    public boolean onCreate() {

        helper = new CompanyDBHelper(getContext());

        if (helper != null){
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor;

        cursor = db.query(CompanyDBHelper.TABLE_NAME, strings, s, strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase db = helper.getWritableDatabase();
        long rowId;

        rowId = db.insert(CompanyDBHelper.TABLE_NAME, null, contentValues);

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.withAppendedPath(uri, rowId + "");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int count;

        count = db.delete(CompanyDBHelper.TABLE_NAME, s, strings);

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int count;

        count = db.update(CompanyDBHelper.TABLE_NAME, contentValues, s, strings);

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
