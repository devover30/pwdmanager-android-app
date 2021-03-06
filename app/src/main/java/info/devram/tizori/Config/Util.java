package info.devram.tizori.Config;

public class Util {
    // DB related items
    public static final int DATABASE_VERSION = 1;
    public static final String DATBASE_NAME = "pwdmanager";
    public static final String TABLE_NAME = "account";

    public static final String KEY_ID = "id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_ACCOUNT_NAME = "account_name";
    public static final String KEY_LOGIN_ID = "login_id";
    public static final String KEY_LOGIN_PWD = "login_pwd";
    public static final String KEY_CREATED_DATE = "created_date";

    public static final String KEY_URL_LOGIN = "http://pwdmanager.devram.info/auth";
    public static final String KEY_URL_ACCOUNTS = "http://pwdmanager.devram.info/accounts";
    //public static final String KEY_URL_USERS = "https://pwdmanager.devram.info/users";
}
