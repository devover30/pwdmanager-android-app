package info.devram.tizori.Interfaces;

import org.json.JSONObject;

public interface HttpAsyncListener {

    public void httpResponse(JSONObject jsonObject, int statusCode);
}
