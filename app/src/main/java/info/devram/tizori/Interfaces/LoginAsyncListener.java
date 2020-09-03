package info.devram.tizori.Interfaces;

import org.json.JSONObject;

public interface LoginAsyncListener {

    public void loginResponse(JSONObject jsonObject,int statusCode);
}
