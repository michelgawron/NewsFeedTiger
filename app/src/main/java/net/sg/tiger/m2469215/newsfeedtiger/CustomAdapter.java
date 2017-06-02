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
 * Created by M2469215 on 01/06/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    private ArrayList<M_News> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtPublished;
        TextView txtNews;
    }

    public CustomAdapter(ArrayList<M_News> data, Context context) {
        super(context, R.layout.row, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        M_News datamodel = (M_News) getItem(position);
        ViewHolder viewHolder;

        final View result;

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

        viewHolder.txtTitle.setText(datamodel.getTitle().equals("") ? "No title" : datamodel.getTitle());
        viewHolder.txtPublished.setText(datamodel.getPublication());
        viewHolder.txtNews.setText(datamodel.getBody().substring(0,
                datamodel.getBody().indexOf(' ', 200)) + "...");
        return convertView;
    }
}
