package info.devram.tizori.Repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.devram.tizori.Config.Util;
import info.devram.tizori.Models.Accounts;
import info.devram.tizori.R;
import info.devram.tizori.core.HttpHandler;

public class ApiAccountRepository {

    private String URL = Util.KEY_URL_ACCOUNTS;
    private HttpHandler httpHandler;
    private String token;
    private Context ctx;

    public ApiAccountRepository(Context context) {
        this.ctx = context;
        this.httpHandler = HttpHandler.getInstance(context);
    }


    public void getApiAccounts(final ApiResponseListener listener) {

        final List<Accounts> accountsList = new ArrayList<>();

        getToken();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                accountsList.add(parseApiResponse(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        listener.getResult(accountsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-auth-token",token);
                return params;
            }
        };


        httpHandler.addToRequestQueue(jsonArrayRequest);

    }

    public void updateApiAccount(Accounts obj) {

    }


    public void addApiAccount(Accounts obj) {

    }


    public void deleteApiAccount(Accounts obj) {

    }

    private void getToken() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(
                String.valueOf(R.string.shared_pref), Context.MODE_PRIVATE
        );

        token = sharedPreferences.getString("token","No Token");

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
