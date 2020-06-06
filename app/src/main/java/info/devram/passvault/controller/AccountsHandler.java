package info.devram.passvault.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import info.devram.passvault.core.HttpHandler;
import info.devram.passvault.Models.Accounts;
import info.devram.passvault.Config.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AccountsHandler {

    private ArrayList<Accounts> accounts = new ArrayList<>();

    private String URL = Util.KEY_URL_ACCOUNTS;

    private int apiRecords;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public void getRecords(final String token, Context context, final AccountListAsync callback) {
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
                        apiRecords = Integer.parseInt(header.getValue());
                        callback.getApiRecords(apiRecords);
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

        HttpHandler
                .getInstance(context)
                .addToRequestQueue(jsonObjectRequest);


    }

    public void getAccounts(final String token, final Context context,
                            final AccountListAsync callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Accounts account = new Accounts();

                                account.setId(
                                        response.getJSONObject(i).getString("id")
                                );
                                account.setType(
                                        response.getJSONObject(i).getString("type")
                                );
                                account.setAccountName(
                                        response.getJSONObject(i).getString("account_name")
                                );
                                account.setLoginId(
                                        response.getJSONObject(i).getString("login_id")
                                );
                                account.setLoginPwd(
                                        response.getJSONObject(i).getString("login_pwd")
                                );
                                String returnValue = parseDate(response.
                                        getJSONObject(i).getString("created_date"));
                                account.setCreatedDate(
                                        returnValue
                                );

                                accounts.add(account);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (null != callback) callback.processFinished(accounts);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sharedPreferences = context
                        .getSharedPreferences("Volley Error",Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("account_error",error.getMessage());
                editor.apply();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-auth-token",token);
                return params;
            }
        };
        HttpHandler
                .getInstance(context)
                .addToRequestQueue(jsonArrayRequest);


    }

    private String parseDate(String created_date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(created_date);
            dateFormat = new SimpleDateFormat("dd MMMM");
            created_date = dateFormat.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return created_date;
    }

}
