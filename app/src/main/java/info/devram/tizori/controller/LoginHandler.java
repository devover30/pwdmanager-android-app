package info.devram.tizori.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import info.devram.tizori.core.HttpHandler;
import info.devram.tizori.Config.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginHandler {

    private JSONObject jsonObject;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public void checkLogin(String userEmail, String userPassword,
                           final Context context, final LoginAsyncResponse callback) {

        jsonObject = new JSONObject();

        try {
            jsonObject.put("user_email",userEmail);
            jsonObject.put("user_pwd",userPassword);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        requestQueue = HttpHandler.getInstance(context.getApplicationContext())
                .getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Util.KEY_URL_LOGIN,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (callback != null) {
                                callback.getToken(response.getString("token"));
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {

                            callback.responseError(true);
                        }else {

                            callback.responseError(false);
                        }

                        sharedPreferences = context
                                .getSharedPreferences("login_handler",Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("login_response", error.getMessage());
                        editor.apply();

                    }
                }

        );

        HttpHandler
                .getInstance(context)
                .addToRequestQueue(jsonObjectRequest);




    }
}
