package edu.auburn.eng.csse.comp3710.team6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;
import edu.auburn.eng.csse.comp3710.team6.database.DummyDatabase;


public class MainActivity extends ActionBarActivity {

    public interface ElGatoFragment {
        public void redrawList();
    }

    public static final String CURRENT_SUBJECT_KEY = "CURRENT_SUBJECT";
    public static final String CURRENT_SECTION_KEY = "CURRENT_SUBJECT";
    public static final String SUBJECS_KEY = "SUBJECTS";


    public static Subject currentSub = null;

    public static ArrayList<Subject> subjects; //Cached list of subjects

    static {
        subjects = new ArrayList(); //Ensure this object always exists even if empty.
    }

    private static MainActivity instance; //Static instance workaround, bad practice I know, but I needed a quick fix.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            toSubjectFragment();
        }
        instance = this; //Again more of this bad practice stuff.

    }

    @Override
    public void onStop() {
        super.onStop();
        DatabaseHelper.getInstance(this).saveDatabase(subjects);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void toSectionFragment(Subject subject) {
                currentSub = subject;

                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frag_container, new SectionFragment())
                .addToBackStack(null)
                .commit();
    }



    public void toNoteActivity(Section sec) {
        Intent i = new Intent(this, NotecardActivity.class);
        i.putExtra(CURRENT_SUBJECT_KEY, currentSub);
        i.putExtra(CURRENT_SECTION_KEY, sec);
        i.putExtra("SubjectArray", subjects);
        startActivity(i);
    }

    public void toSubjectFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frag_container, new SubjectFragment())
                .commit();
    }



}
