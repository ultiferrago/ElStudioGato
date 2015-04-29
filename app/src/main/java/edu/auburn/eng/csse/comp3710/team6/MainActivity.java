package edu.auburn.eng.csse.comp3710.team6;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;
import edu.auburn.eng.csse.comp3710.team6.database.DummyDatabase;


public class MainActivity extends ActionBarActivity {

    public static ArrayList<Subject> subjects;

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subjects = DatabaseHelper.getInstance(this).getSubjects();
        if (subjects.isEmpty()) {
            subjects = DummyDatabase.getDummySubjects();
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frag_container, new SubjectFragment())
                    .commit();
        }
        instance = this;
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

        return super.onOptionsItemSelected(item);
    }

    public static void toSectionFragment(Subject subject, int position) {
        instance.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frag_container, new SectionFragment(subject, position))
                .addToBackStack(null)
                .commit();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class SubjectFragment extends Fragment {
        public SubjectFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.main_fragment, container, false);

            ListView lv = (ListView)rootView.findViewById(R.id.subjectView);
            SubjectAdapter adapter = new SubjectAdapter(this.getActivity(), subjects);
            lv.setAdapter(adapter);

            return rootView;
        }
    }

    @SuppressLint("ValidFragment")
    public static class SectionFragment extends Fragment {
        Subject subject;
        private final int position;
        public SectionFragment(Subject subject, int position) {
            this.subject = subject;
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.main_fragment, container, false);

            ListView lv = (ListView)rootView.findViewById(R.id.subjectView);
            SectionAdapter adapter = new SectionAdapter(this.getActivity(), subject, position);
            lv.setAdapter(adapter);

            return rootView;
        }
    }
}
