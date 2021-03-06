package net.sg.tiger.m2469215.newsfeedtiger;

/**
 * Created by M2469215 on 02/06/2017.
 */

import android.widget.AbsListView;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {
    //region Variables
    /**
     * The minimum number of items to have below your current scroll position
     * before loading more.
     */
    private int visibleThreshold = 10;

    /**
     * The current offset index of data you have loaded
     */
    private int currentPage = 0;

    /**
     * The total number of items in the dataset after the last load
     */
    private int previousTotalItemCount = 0;

    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean loading = true;

    /**
     * Sets the starting page index
     */
    private int startingPageIndex = 0;
    //endregion

    //region Constructor

    /**
     * Base constructor
     */
    public EndlessScrollListener() {
    }

    /**
     * Constructor
     *
     * @param visibleThreshold Minimum invisible items available for the user
     */
    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    /**
     * Constructor
     *
     * @param visibleThreshold Minimum invisible items available for the user
     * @param startPage        Starting page
     */
    public EndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }
    //endregion


    /**
     * Event called whenever a scroll occurs
     *
     * @param view             View passed by the Context
     * @param firstVisibleItem Index of the first visible item
     * @param visibleItemCount How many items are visible
     * @param totalItemCount   Total items loaded
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // * If the total item count is zero and the previous isn't, assume the
        // * list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // * If it's still loading, we check to see if the dataset count has
        // * changed, if so we conclude it has finished loading and update the current page
        // * number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        // * If it isn't currently loading, we check to see if we have breached
        // * the visibleThreshold and need to reload more data.
        // * If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount) {
            loading = onLoadMore(currentPage + 1, totalItemCount);
        }
    }

    /**
     * Defines the process for actually loading more data based on page
     * Returns true if more data is being loaded; returns false if there is no more data to load.
     */
    public abstract boolean onLoadMore(int page, int totalItemsCount);

    /**
     * Event called when the scroll state changes
     * TODO : Check doc for this wierd listener
     * @param view View passe by the Context
     * @param scrollState Actual scroll state
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
    }
}
