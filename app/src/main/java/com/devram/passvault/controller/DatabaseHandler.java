package com.devram.passvault.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devram.passvault.model.AccountsModel;
import com.devram.passvault.util.Util;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATBASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + Util.TABLE_NAME + "(" +
                Util.KEY_ID + " TEXT," + Util.KEY_TYPE + " TEXT," +
                Util.KEY_ACCOUNT_NAME + " TEXT," + Util.KEY_LOGIN_ID + " TEXT," +
                Util.KEY_LOGIN_PWD + " TEXT," + Util.KEY_CREATED_DATE + " TEXT)";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addAccount(AccountsModel accountsModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_ID,accountsModel.getId());
        contentValues.put(Util.KEY_TYPE,accountsModel.getType());
        contentValues.put(Util.KEY_ACCOUNT_NAME,accountsModel.getAccountName());
        contentValues.put(Util.KEY_LOGIN_ID,accountsModel.getLoginId());
        contentValues.put(Util.KEY_LOGIN_PWD,accountsModel.getLoginPwd());
        contentValues.put(Util.KEY_CREATED_DATE,accountsModel.getCreatedDate());

        db.insert(Util.TABLE_NAME,null,contentValues);
    }

    public List<AccountsModel> getAllAccounts() {
        List<AccountsModel> accounts = new ArrayList<>();

        String query = "SELECT * FROM " + Util.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()) {
            do {

                AccountsModel account = new AccountsModel();
                account.setId(cursor
                        .getString(cursor.getColumnIndex(Util.KEY_ID)));
                account.setType(cursor
                        .getString(cursor.getColumnIndex(Util.KEY_TYPE)));
                account.setAccountName(cursor
                        .getString(cursor.getColumnIndex(Util.KEY_ACCOUNT_NAME)));
                account.setLoginId(cursor
                        .getString(cursor.getColumnIndex(Util.KEY_LOGIN_ID)));
                account.setLoginPwd(cursor
                        .getString(cursor.getColumnIndex(Util.KEY_LOGIN_PWD)));
                account.setCreatedDate(cursor
                        .getString(cursor.getColumnIndex(Util.KEY_CREATED_DATE)));
                accounts.add(account);
            }while (cursor.moveToNext());
        }

        return accounts;
    }

    public int getCount() {
        String query = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        int count = cursor.getCount();

        return count;

    }
}
