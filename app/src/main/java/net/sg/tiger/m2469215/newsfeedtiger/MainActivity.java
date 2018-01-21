package net.sg.tiger.m2469215.newsfeedtiger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

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
    //region Variables
    private Context ctx = this;

    /**
     * Our data's intent address when creating a new activity to show a specific document
     */
    public static final String EXTRA_INTENT_SHOW_ONE_DOC = "net.sg.tiger.ShowOneDoc";

    /**
     * WebApp URL
     */
    private static String url =
            "http://ec2-18-217-30-247.us-east-2.compute.amazonaws.com:3000" +
                    "/PUTFUNCTIONHERE/PUTSKIPHERE/PUTLIMITHERE";

    /**
     * Query entered by the user - default value is an empty string
     */
    private String query;

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

    /**
     * Loading icon
     */
    private ProgressBar myPrg;

    /**
     * SearchView used to get an users' query
     */
    private SearchView mySrch;
    //endregion

    //region Methods
    /**
     * Loading n news from the beginning after an update request by the user
     *
     * @param count Number of news to load
     */
    public void getData(final int count) {
        // * Cancelling all previous requests
        MySingleton.getInstance(this).cancelAll();

        // * Showing the loading buttons
        myPrg.setVisibility(View.VISIBLE);

        this.actualPos = 0;
        String urlRequest = url
                .replace("PUTFUNCTIONHERE", "doc")
                .replace("PUTSKIPHERE", Integer.toString(this.actualPos))
                .replace("PUTLIMITHERE", Integer.toString(count));


        // * New JSON object handling our request to our HTTP server
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

                        // * Date format use to format the timestamp loaded from the database
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
                        // * Notifying that the dataset has changed and updating our position
                        adapter.notifyDataSetChanged();
                        actualPos += count;

                        // * Notifying the view that the dataset has changed and updating the position
                        mySwipe.setRefreshing(false);
                        myPrg.setVisibility(View.INVISIBLE);
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
        // * Adding our request to the request list
        MySingleton.getInstance(this).addToRequestQueue(jsReq);
    }

    /**
     * Loads and adds news to our newsfeed when the user is scrolling down
     *
     * @param count Number of news to add
     */
    public void loadData(final int count) {
        // * Cancelling all previous requests
        MySingleton.getInstance(this).cancelAll();

        // * Showing the loading buttons
        myPrg.setVisibility(View.VISIBLE);

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

                        // * Date format use to format the timestamp loaded from the database
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
                        // * Notifying the view that the dataset has changed and updating the position
                        adapter.notifyDataSetChanged();
                        actualPos += count;

                        // * Setting loading icon visibility to false
                        myPrg.setVisibility(View.INVISIBLE);
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
        // * Adding our request to the request queue
        MySingleton.getInstance(this).addToRequestQueue(jsReq);
    }

    /**
     * Getting datas from the mongodb database using the query given by the suer
     *
     * @param query Query given by the user on the search field
     * @param count Number of documents to be loaded from the database
     */
    public void getDataFromQuery(final String query, final int count) {
        //  * Cancelling all previous requests
        MySingleton.getInstance(this).cancelAll();

        // * Setting loading icon visibility to true
        myPrg.setVisibility(View.VISIBLE);

        String urlRequest = url
                .replace("PUTFUNCTIONHERE", "find_one/" + query)
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
                        if (actualPos == 0) {
                            newsFeed.clear();
                        }

                        // * Date format use to format the timestamp loaded from the database
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
                        // * Notifying the view that the dataset has changed and updating the position
                        adapter.notifyDataSetChanged();
                        actualPos += count;


                        // * Setting the refreshing buttons visibility to false to hide them
                        myPrg.setVisibility(View.INVISIBLE);
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
        // * Adding the request to our requests' list
        MySingleton.getInstance(this).addToRequestQueue(jsReq);
    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        query = "";

        // * ProgressBar used to show the user data is loading
        myPrg = (ProgressBar) findViewById(R.id.myPrg);


        // * Overriding animation on activity opening and closing
        ((Activity) ctx).overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        // * Default vars init
        this.newsFeed = new ArrayList<M_News>();
        this.actualPos = 0;
        myList = (ListView) findViewById(R.id.list);

        // * Creating and setting up the array adapter for our ListView
        adapter = new CustomAdapter(this.newsFeed, getApplicationContext());
        myList.setAdapter(adapter);

        // * This layout is used to add refreshing behaviour to our app
        mySwipe = (SwipeRefreshLayout) findViewById(R.id.mySwipe);

        /*
         * SearchView is used to give the user the ability to search documents on the database
         * And to be shown only the ones he wants to get
         */
        mySrch = (SearchView) findViewById(R.id.searchView);

        //region Listeners
        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /**
             * On refresh beahviour
             */
            @Override
            public void onRefresh() {
                if (query.equals("")) {
                    getData(increment);
                } else {
                    // * If refreshing when we made a query, bringing back the first docs
                    actualPos = 0;
                    getDataFromQuery(query, increment);
                }
            }
        });

        // * Setting a listener for scrolling behaviour
        myList.setOnScrollListener(new EndlessScrollListener() {
            /**
             * When the loadMore event is triggered i.e. when the user gets to the bottom
             * @param page Actual page on the ListView
             * @param totalItemsCount Total items of our list
             * @return True if data loaded, false else
             */
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (query.equals("")) {
                    loadData(increment);
                    return true;
                } else {
                    getDataFromQuery(query, increment);
                    return true;
                }

            }
        });

        // * Setting a listener for a click on an article
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

        mySrch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Gets data whenever a query is submitted by the user
             * @param query Query given by the user
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return this.onQueryTextChange(query);
            }

            /**
             * Gets Data whenever a user enters new data
             * @param newText New text query
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals(query) && !newText.equals("")) {
                    query = newText;
                    actualPos = 0;
                    getDataFromQuery(query, increment);
                } else if (newText.equals("")) {
                    actualPos = 0;
                    getData(increment);
                }
                return true;
            }
        });
        //endregion

        // * Finally we load the basic data for our home screen
        getData(increment);

    }
}
