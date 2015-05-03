package edu.auburn.eng.csse.comp3710.team6;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;

/**
 * Created by kennystreit on 4/26/15.
 */
public class NotecardEditFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DatabaseHelper dbHelper;
    SQLiteDatabase myDB;


    public NotecardEditFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.notecard_fragment, container, false);

        rootView.findViewById(R.id.floating_action_button).setVisibility(View.GONE);




        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
       mAdapter = new NotecardEditAdapter(NotecardActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards());
       mRecyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.menu_notecard_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:

                int notecardListIndex = 0;
                for (int i = 0; i < mRecyclerView.getAdapter().getItemCount() * 2; i += 2) {
                    EditText question = (EditText) mRecyclerView.findViewById(i);
                    String questString = question.getText().toString();
                    EditText answer = (EditText) mRecyclerView.findViewById(i + 1);
                    String ansString = answer.getText().toString();

                    NotecardActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards().get(notecardListIndex).setFront(questString);
                    NotecardActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards().get(notecardListIndex).setBack(ansString);
                    MainActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards().get(notecardListIndex).setFront(questString);
                    MainActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards().get(notecardListIndex).setBack(ansString);
                    notecardListIndex++;
                }

                getActivity().getSupportFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
