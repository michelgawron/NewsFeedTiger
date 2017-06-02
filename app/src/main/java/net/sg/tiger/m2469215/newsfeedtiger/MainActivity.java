package net.sg.tiger.m2469215.newsfeedtiger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Context ctx = this;

    //region Variables
    public static final String EXTRA_INTENT_SHOW_ONE_DOC = "net.sg.tiger.ShowOneDoc";

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

    /**
     * Layout used to make refreshing possible
     */
    private SwipeRefreshLayout mySwipe;
    //endregion

    //region Methods

    /**
     * Loading n news from the beginning after an update request by the user
     *
     * @param count Number of news to load
     */
    public void getData(final int count) {
        this.actualPos = 0;
        String urlRequest = url
                .replace("PUTFUNCTIONHERE", "doc")
                .replace("PUTSKIPHERE", Integer.toString(this.actualPos))
                .replace("PUTLIMITHERE", Integer.toString(count));

        /*
         * New JSON object handling our request to our HTTP server
         */

        JsonArrayRequest jsReq = new JsonArrayRequest
                (Request.Method.GET, urlRequest, null, new Response.Listener<JSONArray>() {
                    /**
                     * On response behaviour
                     * We want to display all the news sent back by the API
                     * @param response JSON data containing our news
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        newsFeed.clear();
                        adapter.notifyDataSetChanged();
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
                        actualPos += count;
                        mySwipe.setRefreshing(false);
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

    /**
     * Loads and adds news to our newsfeed when the user is scrolling down
     *
     * @param count Number of news to add
     */
    public void loadData(final int count) {
        String urlRequest = url
                .replace("PUTFUNCTIONHERE", "doc")
                .replace("PUTSKIPHERE", Integer.toString(this.actualPos))
                .replace("PUTLIMITHERE", Integer.toString(count));

        JsonArrayRequest jsReq = new JsonArrayRequest
                (Request.Method.GET, urlRequest, null, new Response.Listener<JSONArray>() {
                    /**
                     * On response behaviour
                     * We want to display all the news sent back by the API
                     * @param response JSON data containing our news
                     */
                    @Override
                    public void onResponse(JSONArray response) {
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
                        actualPos += count;
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
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Activity) ctx).overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        mySwipe = (SwipeRefreshLayout) findViewById(R.id.mySwipe);
        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(increment);
            }
        });

        this.newsFeed = new ArrayList<M_News>();
        this.actualPos = 0;
        myList = (ListView) findViewById(R.id.list);

        /*
         * Creating and setting up the array adapter for our ListView
         */
        adapter = new CustomAdapter(this.newsFeed, getApplicationContext());
        myList.setAdapter(adapter);

        /*
         * Setting a listener for scrolling behaviour
         */
        myList.setOnScrollListener(new EndlessScrollListener() {
            /**
             * When the loadMore event is triggered i.e. when the user gets to the bottom
             * @param page Actual page on the ListView
             * @param totalItemsCount Total items of our list
             * @return True if data loaded, false else
             */
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadData(increment);

                return true;
            }
        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * When clicking on an item from the ListView we are going to open a new activity and
             * To show the selected document to the user
             * @param parent Parent adapterView calling the event
             * @param view View calling the event
             * @param position Position of the clicked item
             * @param id id of the clicked item on the view (if the users wants to add specific
             *           behaviours for specific fields of a View - like an image for example)
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                M_News m = newsFeed.get(position);
                Intent i = new Intent(getApplicationContext(), ShowOne.class);
                i.putExtra(EXTRA_INTENT_SHOW_ONE_DOC, m);
                startActivity(i);
            }
        });

        getData(increment);

    }
}
