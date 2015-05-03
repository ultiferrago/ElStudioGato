package edu.auburn.eng.csse.comp3710.team6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Iterator;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;
import edu.auburn.eng.csse.comp3710.team6.database.DummyDatabase;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kennystreit on 4/29/15.
 */
public class SplashActivity extends Activity {

    /**
     * Duration of wait *
     */
    private final int SPLASH_DISPLAY_LENGTH = 2000; //Increased time.
    private final boolean fakeData = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_layout);

        //Real quick lets initialize the database.

        //Realm independent
        ArrayList<Subject> temp = new ArrayList();


        MainActivity.subjects = DatabaseHelper.getInstance(this).getSubjects();
        if (MainActivity.subjects.isEmpty() && fakeData) {
            MainActivity.subjects = DummyDatabase.getDummySubjects();
        }

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}