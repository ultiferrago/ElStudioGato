package edu.auburn.eng.csse.comp3710.team6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple list view.
 */
public class SubjectFragment extends Fragment implements MainActivity.ElGatoFragment {

    ListView lv;
    SubjectAdapter adapter;

    public SubjectFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            MainActivity.subjects = savedInstanceState.getParcelableArrayList(MainActivity.SUBJECS_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_subject, menu);
    }

    @Override
    public void onSaveInstanceState(Bundle save) {
        super.onSaveInstanceState(save);
        save.putParcelableArrayList(MainActivity.SUBJECS_KEY, MainActivity.subjects);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_subject_item_add) {
            AddSubjectDialog dialog = new AddSubjectDialog();
            dialog.setFrag(this);
            dialog.show(getFragmentManager(), "Apples");
        } else if (id == R.id.menu_subject_item_delete) {
            if (MainActivity.subjects.isEmpty()) {
                Toast.makeText(this.getActivity(), "There are no subjects. Why not try creating one first!", Toast.LENGTH_SHORT).show();
            } else {
                DeleteSubjectDialog dialog = new DeleteSubjectDialog();
                dialog.show(getFragmentManager(), "potato");
            }
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);


        //Get the list view, create an adapter and set it.
        lv = (ListView)rootView.findViewById(R.id.subjectView);
        adapter = new SubjectAdapter(this.getActivity(), MainActivity.subjects);
        lv.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void redrawList() {
        adapter = new SubjectAdapter(this.getActivity(), MainActivity.subjects);
        lv.setAdapter(adapter);
        lv.invalidate();
    }


}
