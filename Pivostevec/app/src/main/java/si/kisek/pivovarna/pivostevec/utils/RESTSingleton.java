package si.kisek.pivovarna.pivostevec.utils;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RESTSingleton {
    private static RESTSingleton instance;
    private static Context context;
    private static RequestQueue requestQueue;

    public RESTSingleton(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }


    public RequestQueue getRequestQueue() {
        if (this.requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return this.requestQueue;
    }

    public static synchronized RESTSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RESTSingleton(context);
        }
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
