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

    public static final String FRAGMENT_SUBJECT = "SubjectFragment";
    public static final String FRAGMENT_SECTION = "SectionFragment";

    public static final String SUBJECT_POSITION_KEY = "SubjectPosition";
    public static final String SECTION_POSITION_KEY = "SectionPosition";

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

        //Will move all this to splash screen. //////////////////////
        subjects = DatabaseHelper.getInstance(this).getSubjects(); //
        if (subjects.isEmpty()) {                                  //
            subjects = DummyDatabase.getDummySubjects();           //
        }                                                          //
        /////////////////////////////////////////////////////////////

        if (savedInstanceState == null) {
            toSubjectFragment();
        }
        instance = this; //Again more of this bad practice stuff.

    }

    @Override
    public void onPause() {
        super.onPause();
        DatabaseHelper.getInstance(this).saveDatabase(subjects);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        DatabaseHelper.getInstance(this).saveDatabase(subjects);
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
                .replace(R.id.main_frag_container, new SectionFragment(subject))
                .addToBackStack(null)
                .commit();
    }

    public void toSectionFragmentNoBack(Subject subject) {
        currentSub = subject;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frag_container, new SectionFragment(subject))
                .commit();
    }


    public void toNoteActivity(Section sec) {
        Intent i = new Intent(this, NotecardActivity.class);
        i.putExtra(SUBJECT_POSITION_KEY, subjects.indexOf(currentSub));
        i.putExtra(SECTION_POSITION_KEY, currentSub.getSections().indexOf(sec));
        i.putExtra("SubjectArray", subjects);
        startActivity(i);
    }

    public void toSubjectFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frag_container, new SubjectFragment())
                .commit();
    }



}
