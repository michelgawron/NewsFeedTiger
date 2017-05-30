package net.sg.tiger.m2469215.newsfeedtiger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M2469215 on 30/05/2017.
 * This class purpose is to be used as a model to handle the data and transfer it to the activity
 * We are going to use a MVC-like pattern to make things easier to implement
 */

public class M_NewsActivity {
    /**
     * URL of our mongodb database
     */
    private final static String URLMONGO = " ";

    /**
     * News feed - we are going to get it from MongoDB
     */
    private ArrayList<M_News> listNews;

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
}
