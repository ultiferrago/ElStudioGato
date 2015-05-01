package edu.auburn.eng.csse.comp3710.team6;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;


public class MainActivity extends ActionBarActivity {

    public static final String FRAGMENT_SUBJECT = "SubjectFragment";
    public static final String FRAGMENT_SECTION = "SectionFragment";
    public static String currentFrag = ""; //Current frag for use with add button.
    public static Subject currentSub = null;

    public static ArrayList<Subject> subjects; //Cached list of subjects

    static {
        subjects = new ArrayList(); //Ensure this object always exists even if empty.
    }

    private static MainActivity instance; //Static instance workaround, bad practice I know, but I needed a quick fix.

    private ArrayList<Subject> subjectArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Will move all this to splash screen. //////////////////////
        subjects = DatabaseHelper.getInstance(this).getSubjects(); //
//        if (subjects.isEmpty()) {                                  //
//            subjects = DummyDatabase.getDummySubjects();           //
//        }                                                          //
        /////////////////////////////////////////////////////////////


        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        subjectArray = dbHelper.getAllSubjects();




        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frag_container, new SubjectFragment())
                    .commit();
        }
        instance = this; //Again more of this bad practice stuff.

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
//        DatabaseHelper.getInstance(this).saveDatabase(subjects);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.menu_item_add) {
            //The add button was clicked. Bring up dialogue for adding subject.

            FragmentManager fm = this.getSupportFragmentManager();
            AddDialog dialog = new AddDialog();
            Bundle bundle = new Bundle();
            if (currentSub != null) bundle.putString("subjectName", currentSub.getSubjectName());
            else bundle.putString("subjectName", "");
            dialog.setArguments(bundle);
            dialog.show(fm, "add_subject");
        }

        if (id == R.id.menu_item_delete) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frag_container, new OrganizationDeleteFragment())
                .addToBackStack(null)
                .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void toSectionFragment(Subject subject) {
        currentSub = subject;

        instance.getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.main_frag_container, new SectionFragment(subject))
            .addToBackStack(null)
            .commit();
    }

    public void getAllSubjects() {

    }

}
