package net.sg.tiger.m2469215.newsfeedtiger;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //region Variables
    /**
     * WebApp URL
     */
    private static String url =
            "http://tigernode.azurewebsites.net/PUTFUNCTIONHERE/PUTSKIPHERE/PUTLIMITHERE";

    /**
     * Number of news to be loaded
     */
    private static int increment = 20;

    /**
     * Actual position of the user when they are swiping down
     */
    private int actualPos;

    /**
     * Tiger news feed
     */
    private ArrayList<M_News> newsFeed;

    /**
     * Array adapter handling the data on our ListView
     */
    CustomAdapter adapter;
    //endregion

    //region Android objects
    /**
     * ListView used to display our List
     */
    private ListView myList;
    //endregion

    //region Methods
    /**
     * Loading n news from the beginning after an update request by the user
     *
     * @param count Number of news to load
     */
    public void getData(int count) {
        this.actualPos = 0;
        url = url
                .replace("PUTFUNCTIONHERE", "doc")
                .replace("PUTSKIPHERE", Integer.toString(this.actualPos))
                .replace("PUTLIMITHERE", Integer.toString(count));

        /*
         * New JSON object handling our request to our HTTP server
         */

        JsonArrayRequest jsReq = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    /**
                     * On response behaviour
                     * We want to display all the news sent back by the API
                     * @param response JSON data containing our news
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        newsFeed.clear();
                        /*
                         * Date format use to format the timestamp loaded from the database
                         */
                        DateFormat df = new SimpleDateFormat("dd/MM/yyy");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                /*
                                 * Adding a news to our news feed
                                  * Getting the title and the body with no changes
                                  * Changing the date in order to get a string date (instead of a timestamp)
                                  * Getting the nested JSONArray to get href value
                                 */
                                newsFeed.add(new M_News(obj.getString("yandexTranslationTitle"),
                                        df.format(new Date(obj.getLong("published"))),
                                        obj.getString("yandexTranslationBody"),
                                        obj.getJSONArray("alternate")
                                                .getJSONObject(0)
                                                .getString("href")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    /**
                     * On error behaviour
                     * Logging the error message
                     * @param error Error message sent by the request
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR CONNECTION", error.toString());
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsReq);
    }

    /**
     * Method handling a click on our main_button
     * Sending an HTTP request to our REST API and getting the news - storing it in a List
     * And displaying it on the Activity View
     *
     * @param view Reference to the clicked widget
     */
    public void getList(View view) {
        getData(increment);
    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.newsFeed = new ArrayList<M_News>();
        this.actualPos = 0;
        myList = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this.newsFeed, getApplicationContext());
        myList.setAdapter(adapter);
    }
}
