package net.sg.tiger.m2469215.newsfeedtiger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Custom adapter used to parse an ArrayList to a ListView with custom xml view for each row
 * Created by M2469215 on 01/06/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    //region Variables
    /**
     * Dataset to parse
     */
    private ArrayList<M_News> dataSet;

    /**
     * Context on which the dataset should be displayed
     */
    Context mContext;
    //endregion

    /**
     * Class used to hold our rows
     * Basically, it stores our View items
     */
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtPublished;
        TextView txtNews;
    }

    /**
     * "Basic" constructor for our Custom Adapter
     * @param data Data array to parse to the view
     * @param context Context on which the data should be parsed
     */
    public CustomAdapter(ArrayList<M_News> data, Context context) {
        super(context, R.layout.row, data);
        this.dataSet = data;
        this.mContext = context;
    }

    /**
     * Gets the view and inflates it with our data
     * @param position Item position
     * @param convertView View to be converted - here our rows
     * @param parent Parent view to which the items should be attached
     * @return A rendered View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // * Single record of our datas
        M_News datamodel = (M_News) getItem(position);
        ViewHolder viewHolder;

        // * Result view returned to the user
        final View result;

        // * If we got no basic View for our rows then creating one
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.Title);
            viewHolder.txtPublished = (TextView) convertView.findViewById(R.id.date);
            viewHolder.txtNews = (TextView) convertView.findViewById(R.id.newsSummary);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        // * Setting the data to be displayed and returning the view
        viewHolder.txtTitle.setText(datamodel.getTitle().equals("") ? "No title" : datamodel.getTitle());
        viewHolder.txtPublished.setText(datamodel.getPublication());
        viewHolder.txtNews.setText(datamodel.getBody().substring(0,
                datamodel.getBody().indexOf(' ', 200)) + "...");
        return convertView;
    }
}
