package info.devram.passvault.Repository;

import org.json.JSONArray;

public interface ApiResponseListener<T> {

    public void getResult(T response);
}
