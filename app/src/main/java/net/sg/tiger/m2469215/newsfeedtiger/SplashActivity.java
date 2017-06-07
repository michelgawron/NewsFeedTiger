package net.sg.tiger.m2469215.newsfeedtiger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity called when the app is launched
 * This activity is only useful for design purposes - it immediately starts the MainActivity
 * Created by M2469215 on 02/06/2017.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // * Creating the MainActivity with no further delay
        // * The activity will be shown after being fully loaded
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
