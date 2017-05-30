package net.sg.tiger.m2469215.newsfeedtiger;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

/**
 * Created by M2469215 on 30/05/2017.
 * This class purpose is to be used as a model to handle the data and transfer it to the activity
 * We are going to use a MVC-like pattern to make things easier to implement
 */

public class M_NewsActivity {
    /**
     * URL of our mongodb database
     */
    private final static String URLMONGO = "52.233.183.232";

    /**
     * MongoDB port
     */
    private final static int PORTMONGO = 27017;

    /**
     * MongoDB database name
     */
    private final static String DATABASENAME = "local";

    /**
     * MongoDB collection name
     */
    private final static String COLLECTIONNAME = "documents";


    /**
     * News feed - we are going to get it from MongoDB
     */
    private ArrayList<M_News> listNews;

    /**
     * Current articles loaded (0 at the beginning, loading 20 more each time)
     */
    private int currentLoad;

    /**
     *
     * @return
     */
    public int getCurrentLoad() {
        return currentLoad;
    }

    /**
     *
     * @param currentLoad
     */
    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    /**
     * Getter for the news list
     *
     * @return News feed
     */
    public ArrayList<M_News> getListNews() {
        return listNews;
    }

    /**
     * Constructor for our news model - list init
     */
    public M_NewsActivity() {
        this.listNews = new ArrayList<M_News>();
        this.currentLoad = 0;
    }

    /**
     * This function is going to update our news list after a request to the mongodb
     * It is going to be used after an update request from the user
     * @param newList New list of news
     */
    public void updateList(ArrayList<M_News> newList) {

    }

    /**
     * This function is going to add news to our news list
     * It is going to be used when a user wants to go deeper than the original amount of news given
     * @param newList News list to add
     */
    public void addNews(ArrayList<M_News> newList)
    {

    }

    /**
     * Getting a list of news from mongodb
     * @param start Index start
     * @param end Index end
     * @return A list of news to be displayed
     */
    public ArrayList<M_News> getFromMongo(int start, int end)
    {
        MongoClient client = new MongoClient(URLMONGO, PORTMONGO);
        MongoCollection<Document> coll = client.getDatabase(DATABASENAME).getCollection(COLLECTIONNAME);

    }
}
