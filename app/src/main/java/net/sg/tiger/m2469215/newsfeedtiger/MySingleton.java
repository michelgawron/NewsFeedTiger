package net.sg.tiger.m2469215.newsfeedtiger;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton used to handle HTTP Requests
 * As suggested by its name, there is only one instance of Singleton at the execution of our app
 * Created by M2469215 on 01/06/2017.
 */

public class MySingleton {
    /**
     * Instance of the singleton
     */
    private static MySingleton mInstance;

    /**
     * Request queue where our requests are stored before being fired
     */
    private RequestQueue mRequestQueue;

    /**
     * Context that needs to be passed in order to make the Singleton work
     */
    private static Context mCtx;

    /**
     * Constructor for the Singleton
     * @param context Context
     */
    private MySingleton(Context context) {
        mCtx = context;
        // * Calling getRequestQueue will create it only if there is no other
        mRequestQueue = getRequestQueue();
    }

    /**
     * Getting the instance of Singleton
     * @param context Context on which the Singleton is used
     * @return A Singleton
     */
    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    /**
     * Getting request queue - creating one if there is no other
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // * getApplicationContext() is key, it keeps you from leaking the
            // * Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Adding a request to the request queue
     * @param req Request to add
     * @param <T> Type of the Request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Cancel all requests using a requests filter always returning true
     * This will ensure that any request response will not be received
     */
    public void cancelAll(){
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter(){
            /**
             * Apply the filter
             * @param request A single request to filter
             * @return True if the request matches, false else
             */
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
