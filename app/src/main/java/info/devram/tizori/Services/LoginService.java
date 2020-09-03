package info.devram.tizori.Services;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import info.devram.tizori.Config.Util;
import info.devram.tizori.Interfaces.HttpAsyncListener;
import info.devram.tizori.Models.UserEntity;

public class LoginService implements Runnable {

    private static final String TAG = "LoginHandler";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private UserEntity userEntity;
    private HttpAsyncListener mListener;
    private int responseCode;

    public LoginService(UserEntity userEntity, HttpAsyncListener mListener) {
        this.userEntity = userEntity;
        this.mListener = mListener;
    }

    private String checkLogin() {
        Log.d(TAG, "checkLogin: starts");
        HttpURLConnection connection = null;
        BufferedReader bufferedReader;

        if (userEntity == null) {
            return null;
        }

        try {
            String userCredentials = userEntity.getUser_email() + ":" + userEntity.getUser_pwd();
            byte[] encodedCredentials = Base64.encode(userCredentials.getBytes(), Base64.DEFAULT);
            String basicAuth = "Basic " + new String(encodedCredentials);
            URL url = new URL(Util.KEY_URL_LOGIN);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setDoOutput(true);

            responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                StringBuilder result = new StringBuilder();
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    result.append(line).append("\n");
                }
                Log.d(TAG, "checkLogin: ends status error");
                return result.toString();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder result = new StringBuilder();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                result.append(line).append("\n");
            }
            Log.d(TAG, "checkLogin: ends status ok");
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            Log.d(TAG, "checkLogin: ends");
        }

        return null;

    }

    @Override
    public void run() {
        Log.d(TAG, "run: starts");
        String data = checkLogin();
        Log.d(TAG, "run: " + data);
        try {
            if (data != null) {
                JSONObject jsonObject = new JSONObject(data);
                mListener.httpResponse(jsonObject, responseCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "run: ends");
    }
}
