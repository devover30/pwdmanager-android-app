package info.devram.tizori.core;

import android.content.Context;

public class HttpHandler {

    private static HttpHandler instance;



    private static Context ctx;

    private HttpHandler(Context context) {
        ctx = context;

    }

    public static synchronized HttpHandler getInstance(Context context) {
        if (instance == null) {
            instance = new HttpHandler(context);
        }

        return instance;
    }
}