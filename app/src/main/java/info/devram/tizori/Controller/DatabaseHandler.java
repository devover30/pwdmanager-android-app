package info.devram.tizori.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.devram.tizori.Models.Accounts;
import info.devram.tizori.Config.Util;
import info.devram.tizori.Interfaces.ModelsHandler;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler mInstance = null;
    private ModelsHandler model = new Accounts.Model();
    private SQLiteDatabase database;

    private DatabaseHandler(Context context) {
        super(context, Util.DATBASE_NAME, null, Util.DATABASE_VERSION);
    }

    public static DatabaseHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHandler(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        model.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public SQLiteDatabase getWritableDatabase() {
        database = super.getWritableDatabase();
        return database;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        database = super.getReadableDatabase();
        return database;
    }

    @Override
    public synchronized void close() {
        super.close();
        database.close();
    }
}
