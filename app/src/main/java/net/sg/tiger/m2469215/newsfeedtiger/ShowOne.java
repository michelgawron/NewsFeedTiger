package net.sg.tiger.m2469215.newsfeedtiger;

import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by M2469215 on 02/06/2017.
 */

public class ShowOne extends AppCompatActivity {
    //region Variables
    /**
     * Context
     */
    private Context ctx = this;

    /**
     * Body view
     */
    private TextView Body;

    /**
     * Title View
     */
    private TextView Title;

    /**
     * Date view
     */
    private TextView Date;

    /**
     * Link view
     */
    private TextView Link;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showone);

        // * Linking our objects and getting the data from the intent
        Date = (TextView) findViewById(R.id.Date);
        Link = (TextView) findViewById(R.id.Link);
        Body = (TextView) findViewById(R.id.Body);
        Title = (TextView) findViewById(R.id.Title);
        Intent i = getIntent();

        // * Overriding animations for this view
        ((Activity) ctx).overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        // * Getting the document's values to display it
        final M_News m = i.getExtras().getParcelable(MainActivity.EXTRA_INTENT_SHOW_ONE_DOC);
        Date.setText(m.getPublication());
        Body.setText(m.getBody());
        Title.setText(m.getTitle().equals("") ? "No title" : m.getTitle());

        // * A click on the link opens up a chrome page
        Link.setOnClickListener(new View.OnClickListener() {
            /**
             * Event called when a click on the link occurs
             * @param v View passed by the context
             */
            @Override
            public void onClick(View v) {
                // * Getting link and opening it on a Web Browser - we need an intent to call it
                String urlString = m.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");

                try {
                    // * Trying to call Chrome directly
                    ctx.startActivity(i);
                } catch (ActivityNotFoundException ex) {
                    // * If Chrome is not installed, asking the user to choose a browser
                    i.setPackage(null);
                    ctx.startActivity(i);
                }
            }
        });
    }

    /**
     * Event called when the user decides to get back to the articles' list
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // * Changing the animation to get fade effect
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
