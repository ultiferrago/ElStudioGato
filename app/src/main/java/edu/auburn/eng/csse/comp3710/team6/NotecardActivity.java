package edu.auburn.eng.csse.comp3710.team6;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kennystreit on 4/24/15.
 */
public class NotecardActivity extends ActionBarActivity {

    private ArrayList<Note> notecardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecard_frag_holder);

        /*JsonStorage jsonStorage = new JsonStorage(this);
        try {
            String notecardJSON = jsonStorage.readNotecardsAsset();
            Log.d("KENNY", "Drink JSON: " + notecardJSON);
            notecardList = jsonStorage.createNotecardItem(notecardJSON);
            Log.d("KENNY", "Success!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException ej) {
            Log.d("KENNY", "JSON Failed: " + ej);
            ej.printStackTrace();
        }*/

        if (savedInstanceState == null) {
            int subPos = getIntent().getIntExtra("SubjectPos", 0);
            int sectionPos = getIntent().getIntExtra("SectionPos", 0);
            notecardList = MainActivity.subjects.get(subPos).getSections().get(sectionPos).getNoteCards();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(notecardList))
                    .commit();
            Log.i("Notecards", "Bundle was null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notecard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(getApplicationContext(), "Edit Clicked!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), NotecardEditActivity.class);
//                startActivity(intent);
                createFragment(new NotecardEditFragment(notecardList));
                return true;
            case R.id.delete:
                Toast.makeText(getApplicationContext(), "Delete Clicked!", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;

        ArrayList<Note> notecardList = new ArrayList<>();

        public PlaceholderFragment() {

        }

        @SuppressLint("ValidFragment")
        public PlaceholderFragment(ArrayList<Note> notecardList) {
            this.notecardList = notecardList;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.notecard_fragment, container, false);

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new NotecardAdapter(notecardList);
            mRecyclerView.setAdapter(mAdapter);
            return rootView;
        }
    }

    /**
     * Creates the fragment of your choice
     * @param fragment fragment type (either MainHaikuFragment or DisplayHaikuFragment)
     */
    private void createFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
