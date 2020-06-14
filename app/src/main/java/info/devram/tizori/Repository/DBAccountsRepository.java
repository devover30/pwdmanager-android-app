package info.devram.tizori.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import info.devram.tizori.Config.Util;
import info.devram.tizori.Models.Accounts;
import info.devram.tizori.controller.DatabaseHandler;


public class DBAccountsRepository implements
        DatabaseService<Accounts> {

    private DatabaseHandler db;



    public DBAccountsRepository(Context context) {

        this.db = DatabaseHandler.getInstance(context);

    }

    @Override
    public Boolean addData(Accounts obj) {

        try{

            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.KEY_TYPE,obj.getType());
            contentValues.put(Util.KEY_ACCOUNT_NAME,obj.getAccountName());
            contentValues.put(Util.KEY_LOGIN_ID,obj.getLoginId());
            contentValues.put(Util.KEY_LOGIN_PWD,obj.getLoginPwd());
            contentValues.put(Util.KEY_CREATED_DATE,obj.getCreatedDate());

            db.getWritableDatabase().insert(Util.TABLE_NAME,null,contentValues);

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Accounts> getAll() {
        List<Accounts> accounts = new ArrayList<>();

        String query = "SELECT * FROM " + Util.TABLE_NAME;

        Cursor cursor = db.getReadableDatabase().rawQuery(query,null);

        if(cursor.moveToFirst()) {
            do {

                Accounts account = new Accounts();
                account.setId(cursor
                        .getInt(cursor.getColumnIndex(Util.KEY_ID)));
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

        cursor.close();

        return accounts;

    }

    @Override
    public Accounts getOne() {
        return null;
    }

    @Override
    public int onUpdate(Accounts obj) {
        return 0;
    }

    @Override
    public void onDelete(Accounts obj) {
        db.getWritableDatabase().delete(Util.TABLE_NAME,Util.KEY_ID + "=?",
                new String[]{String.valueOf(obj.getId())});
    }

    @Override
    public int getCount() {
        String query = "SELECT * FROM " + Util.TABLE_NAME;

        Cursor cursor = db.getReadableDatabase().rawQuery(query,null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }
}
