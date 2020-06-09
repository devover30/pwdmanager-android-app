package info.devram.passvault.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import info.devram.passvault.Config.Util;
import info.devram.passvault.Models.Accounts;
import info.devram.passvault.controller.DatabaseHandler;

public class AccountsRepository implements DatabaseService<Accounts> {

    private DatabaseHandler db;

    public AccountsRepository(Context context) {
        this.db = DatabaseHandler.getInstance(context);
    }

    @Override
    public void addData(Accounts obj) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_ID,obj.getId());
        contentValues.put(Util.KEY_TYPE,obj.getType());
        contentValues.put(Util.KEY_ACCOUNT_NAME,obj.getAccountName());
        contentValues.put(Util.KEY_LOGIN_ID,obj.getLoginId());
        contentValues.put(Util.KEY_LOGIN_PWD,obj.getLoginPwd());
        contentValues.put(Util.KEY_CREATED_DATE,obj.getCreatedDate());

        db.getWritableDatabase().insert(Util.TABLE_NAME,null,contentValues);
    }

    @Override
    public MutableLiveData<List<Accounts>> getAll() {
        List<Accounts> accounts = new ArrayList<>();

        String query = "SELECT * FROM " + Util.TABLE_NAME;

        Cursor cursor = db.getReadableDatabase().rawQuery(query,null);

        if(cursor.moveToFirst()) {
            do {

                Accounts account = new Accounts();
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

        MutableLiveData<List<Accounts>> mutableLiveData = new MutableLiveData<>();

        mutableLiveData.setValue(accounts);

        cursor.close();

        return mutableLiveData;

    }

    @Override
    public MutableLiveData<Accounts> getOne() {
        return null;
    }

    @Override
    public int onUpdate(Accounts obj) {
        return 0;
    }

    @Override
    public void onDelete(Accounts obj) {

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
