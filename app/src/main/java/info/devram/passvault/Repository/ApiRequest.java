package info.devram.passvault.Repository;

import android.util.Log;
import androidx.annotation.Nullable;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApiRequest<T> extends Request<T> {

    private String token;
    private int method;
    private ApiResponseListener apiResponseListener;

    public ApiRequest(int method, String url,
                      @Nullable Response.ErrorListener listener,
                      String token,ApiResponseListener apiResponseListener) {
        super(method, url, listener);
        this.token = token;
        this.method = method;
        this.apiResponseListener = apiResponseListener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        JSONObject responseJsonObject = null;
        JSONArray responseJsonArray = null;
        String responseContentLength = null;

        switch (method) {
            case Method.HEAD:
                List<Header> headerList = response.allHeaders;

                for (Header header : headerList) {
                    if (header.getName().equals("Content-Length")) {
                        responseContentLength = header.getValue();
                    }
                }
                return Response.success(responseContentLength,
                        HttpHeaderParser.parseCacheHeaders(response));
            case Method.GET:
                if (response.statusCode != 200) {
                    return Response.error(new ParseError());
                }
                try {
                    responseJsonArray = new JSONArray(new String(response.data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(responseJsonArray,
                        HttpHeaderParser.parseCacheHeaders(response));

        }
        try {
            responseJsonObject = new JSONObject(new String(response.data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Response.success(responseJsonObject,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {

    }


    @Override
    public void deliverError(VolleyError error) {
        Log.i("error", "deliverError: " + error.networkResponse);
    }

    @Override
    public Map<String, String> getHeaders()  {
        Map<String, String> params = new HashMap<>();
        params.put("x-auth-token",token);
        return params;


    }


}
