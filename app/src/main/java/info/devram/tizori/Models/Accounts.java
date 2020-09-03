package info.devram.tizori.Models;

import android.database.sqlite.SQLiteDatabase;

import info.devram.tizori.Config.Util;
import info.devram.tizori.Interfaces.ModelsHandler;

public class Accounts {

    private String id;
    private String type;
    private String accountName;
    private String loginId;
    private String loginPwd;
    private String createdDate;

    public Accounts() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(String createdDate) {

        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", accountName='" + accountName + '\'' +
                ", loginId='" + loginId + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }

    public static class Model implements ModelsHandler {

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE IF NOT EXISTS " + Util.TABLE_NAME + "(" +
                    Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_TYPE + " TEXT," +
                    Util.KEY_ACCOUNT_NAME + " TEXT," + Util.KEY_LOGIN_ID + " TEXT," +
                    Util.KEY_LOGIN_PWD + " TEXT," + Util.KEY_CREATED_DATE + " TEXT)";

            db.execSQL(query);
        }
    }
}
