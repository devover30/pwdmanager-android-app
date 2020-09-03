package info.devram.tizori.Services;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import info.devram.tizori.Config.Util;
import info.devram.tizori.Interfaces.ApiResponseListener;
import info.devram.tizori.Interfaces.HttpAsyncListener;
import info.devram.tizori.Models.Accounts;

public class GetAccountsService implements Runnable, HttpAsyncListener {

    private static final String TAG = "GetAccountsService";

    private ApiResponseListener mListener;
    private String token;
    private int responseCode;

    public GetAccountsService(String loginToken, ApiResponseListener listener) {
        this.token = loginToken;
        this.mListener = listener;
    }

    @Override
    public void run() {
        Log.d(TAG, "run: starts");

        String data = getAccounts();

        if (data != null) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                httpResponse(jsonObject,responseCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Log.d(TAG, "run: ends");
    }

    private String getAccounts() {
        Log.d(TAG, "getAccounts: starts");

        HttpURLConnection connection = null;
        BufferedReader bufferedReader;

        if (token == null) {
            return null;
        }

        try {
            String bearerAuth = "Bearer " + token;

            URL url = new URL(Util.KEY_URL_ACCOUNTS);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", bearerAuth);

            responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                StringBuilder result = new StringBuilder();
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    result.append(line).append("\n");
                }
                Log.d(TAG, "getAccounts: end status error");
                return result.toString();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder result = new StringBuilder();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                result.append(line).append("\n");
            }

            Log.d(TAG, "getAccounts: end ok status");
            return result.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            Log.d(TAG, "getAccounts: ends");
        }

        return null;
    }

    @Override
    public void httpResponse(JSONObject jsonObject, int statusCode) {
        Log.d(TAG, "httpResponse: starts");
        if (statusCode == 200) {
            List<Accounts> accountsList = new ArrayList<>();
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("msg");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject resObject = jsonArray.getJSONObject(i);
                    Accounts accounts = new Accounts();
                    accounts.setId(resObject.getString("id"));
                    accounts.setType(resObject.getString("type"));
                    accounts.setAccountName(resObject.getString("accountName"));
                    accounts.setLoginId(resObject.getString("loginId"));
                    accounts.setLoginPwd(resObject.getString("loginPwd"));
                    accounts.setCreatedDate(resObject.getString("created"));
                    accountsList.add(accounts);
                }
                this.mListener.getResult(accountsList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "httpResponse: ends");
    }
}
