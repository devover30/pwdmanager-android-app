package info.devram.tizori.core;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class HttpHandler {

    private static HttpHandler instance;
    private RequestQueue requestQueue;


    private static Context ctx;

    private HttpHandler(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized HttpHandler getInstance(Context context) {
        if (instance == null) {
            instance = new HttpHandler(context);
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}