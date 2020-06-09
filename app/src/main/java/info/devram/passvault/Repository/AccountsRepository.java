package info.devram.passvault.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.devram.passvault.Config.Util;
import info.devram.passvault.Models.Accounts;
import info.devram.passvault.R;
import info.devram.passvault.controller.DatabaseHandler;
import info.devram.passvault.core.HttpHandler;

public class AccountsRepository implements
        DatabaseService<Accounts>,APIService<Accounts> {

    private DatabaseHandler db;
    private HttpHandler httpHandler;
    private String URL = Util.KEY_URL_ACCOUNTS;
    private Context ctx;
    private String token;
    private ApiRequest apiRequest;


    public AccountsRepository(Context context) {
        this.ctx = context;
        this.db = DatabaseHandler.getInstance(context);
        this.httpHandler = HttpHandler.getInstance(context);
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


    @Override
    public MutableLiveData<List<Accounts>> getApiAccounts() {

        MutableLiveData<List<Accounts>> mutableLiveData = new MutableLiveData<>();

        final List<Accounts> accountsList = new ArrayList<>();

        getToken();

        apiRequest = new ApiRequest<JSONArray>(Request.Method.GET,
                URL, null, token, new ApiResponseListener<JSONArray>() {
            @Override
            public void getResult(JSONArray response)  {
                Log.i("get", "getResult: " + response);

                for (int i = 0; i < response.length(); i++) {
                    try {
                        accountsList.add(parseApiResponse(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        httpHandler.addToRequestQueue(apiRequest);

        mutableLiveData.setValue(accountsList);

        return mutableLiveData;
    }

    @Override
    public int getApiAccountsCount() {

        final int[] apiRecords = new int[1];

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.HEAD,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                List<Header> headers = response.allHeaders;

                assert headers != null;
                for (Header header: headers) {
                    if (header.getName().equals("Content-Length")) {
                        apiRecords[0] = Integer.parseInt(header.getValue());

                    }
                }

                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-auth-token",token);
                return params;
            }
        };

        httpHandler.addToRequestQueue(jsonObjectRequest);

        return apiRecords[0];
    }

    @Override
    public Boolean addApiAccount(Accounts obj) {
        return null;
    }

    @Override
    public Boolean updateApiAccount(Accounts obj) {
        return null;
    }

    @Override
    public Boolean deleteApiAccount(Accounts obj) {
        return null;
    }

    private void getToken() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(
                String.valueOf(R.string.shared_pref), Context.MODE_PRIVATE
        );

        token = sharedPreferences.getString("token","No Token");

    }

    public void getApiRecord() {
        getToken();

        apiRequest = new ApiRequest(Request.Method.HEAD,
                URL, null, token, new ApiResponseListener<String>() {
            @Override
            public void getResult(String response) {

            }
        });

        httpHandler.addToRequestQueue(apiRequest);
    }

    private Accounts parseApiResponse(JSONObject response) throws JSONException {
        Accounts accountModel = new Accounts();

        accountModel.setType(response.getString("type"));
        accountModel.setAccountName(response.getString("account_name"));
        accountModel.setLoginId(response.getString("login_id"));
        accountModel.setLoginPwd(response.getString("login_pwd"));
        accountModel.setCreatedDate(response.getString("created_date"));

        return accountModel;
    }
}
