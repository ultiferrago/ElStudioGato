package edu.auburn.eng.csse.comp3710.team6;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;

/**
 * Created by kennystreit on 4/24/15.
 */
public class NotecardActivity extends ActionBarActivity {

    public static ArrayList<Subject> subjects = new ArrayList();
    public static int sectionPos = 0;
    public static int subjectPos = 0;

    public static NotecardAdapter mAdapter = null;

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        DatabaseHelper.getInstance(this).saveDatabase(MainActivity.subjects);
    }

    @Override
    public void onSaveInstanceState(Bundle save) {
        super.onSaveInstanceState(save);
        save.putSerializable("selected", mAdapter.getSelectedMap());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecard_frag_holder);

        subjects = getIntent().getParcelableArrayListExtra(MainActivity.SUBJECS_KEY);
        sectionPos = getIntent().getIntExtra(MainActivity.SECTION_POSITION_KEY, 0);
        subjectPos = getIntent().getIntExtra(MainActivity.SUBJECT_POSITION_KEY, 0);

        if (savedInstanceState != null) {
            mAdapter = new NotecardAdapter(null, subjects.get(subjectPos).getSections().get(sectionPos).getNoteCards());
            mAdapter.setSelectedMap((HashMap<Integer, String>)savedInstanceState.getSerializable("selected"));
        } else {
            mAdapter = null;
            PlaceholderFragment frag = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, frag)
                    .commit();
        }

    }


    public void refresh() {
        for (Fragment frag : getSupportFragmentManager().getFragments()) {
            if (frag instanceof PlaceholderFragment) {
                ((PlaceholderFragment)frag).refreshContent();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notecard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                if (subjects.get(subjectPos).getSections().get(sectionPos).getNoteCards().isEmpty()) {
                    Toast.makeText(this, "No notecards to edit! Why not try creating one?", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    NotecardEditFragment frag = new NotecardEditFragment();
                    createFragment(frag);
                    return true;
                }
            case R.id.delete:
                if (subjects.get(subjectPos).getSections().get(sectionPos).getNoteCards().isEmpty()) {
                    Toast.makeText(this, "No notecards to delete! Why not try creating one?", Toast.LENGTH_SHORT).show();
                    return true;
                }
                DeleteNoteDialog dnl = new DeleteNoteDialog();
                dnl.show(getSupportFragmentManager(), "");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * Creates the fragment of your choice
     * @param fragment fragment type
     */
    private void createFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private RecyclerView mRecyclerView;
        private RecyclerView.LayoutManager mLayoutManager;

        SwipeRefreshLayout mSwipeRefreshLayout;

        TextView percent;
        public PlaceholderFragment() {
            this.setHasOptionsMenu(true);
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.notecard_fragment, container, false);

            if (rootView.findViewById(R.id.floating_action_button).getVisibility() == View.GONE) {
                rootView.findViewById(R.id.floating_action_button).setVisibility(View.VISIBLE);
            }

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

            mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
            mSwipeRefreshLayout.setColorScheme(R.color.cat_color, R.color.green, R.color.red);

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshContent();
                }
            });

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example) if not yet created.
            if (mAdapter == null) {
                mAdapter = new NotecardAdapter(null, (ArrayList < Note >) subjects.get(subjectPos).getSections().get(sectionPos).getNoteCards().clone());
            }
            percent = (TextView)rootView.findViewById(R.id.percentage_text);
            mAdapter.setTextView(percent);
            percent.setText(mAdapter.rightPercent() + "%");
            mRecyclerView.setAdapter(mAdapter);

            ImageView fab = (ImageView) rootView.findViewById(R.id.floating_action_button);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createNoteDialog();
                }
            });

            ((NotecardActivity)getActivity()).setActionBarTitle(subjects.get(subjectPos).getSections().get(sectionPos).getName());
            return rootView;
        }

        private void createNoteDialog() {
            AddNoteDialog dialog = new AddNoteDialog();
            dialog.show(getFragmentManager(), "");
        }

        /******************************************************************************************
         * Refreshes the subject list
         * The 2000 is sort of a 'dummy' value to make refresh seem like its doing a lot haha
         ******************************************************************************************/
        public void refreshContent() {

            Toast.makeText(getActivity(), "Restarting...", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //mAdapter = new NotecardAdapter(subjects.get(subjectPos).getSections().get(sectionPos).getNoteCards());
                    mAdapter.updateNotes(subjects.get(subjectPos).getSections().get(sectionPos).getNoteCards());
                    mAdapter.setSelectedMap(new HashMap());

                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.invalidate();
                    mSwipeRefreshLayout.setRefreshing(false);
                    percent.setText("0%");
                }
            }, 500);
        }
    }
}
