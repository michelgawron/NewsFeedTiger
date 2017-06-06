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
        Date = (TextView) findViewById(R.id.Date);
        Link = (TextView) findViewById(R.id.Link);
        Body = (TextView) findViewById(R.id.Body);
        Title = (TextView) findViewById(R.id.Title);
        Intent i = getIntent();

        ((Activity) ctx).overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        final M_News m = i.getExtras().getParcelable(MainActivity.EXTRA_INTENT_SHOW_ONE_DOC);
        Date.setText(m.getPublication());
        Body.setText(m.getBody());
        Title.setText(m.getTitle().equals("") ? "No title" : m.getTitle());
        /*
         * A click on the link opens up a chrome page
         */
        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = m.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");

                try {
                    // Starting up Chrome
                    ctx.startActivity(i);
                } catch (ActivityNotFoundException ex) {
                    // If Chrome is not installed, asking the user to choose a browser
                    i.setPackage(null);
                    ctx.startActivity(i);                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
