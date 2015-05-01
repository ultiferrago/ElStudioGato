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
 * A placehold fragment containing a simple list view.
 */
public class SectionFragment extends Fragment {
    Subject subject; //What subject are we inside of.
    private int position; //What position was this subject in the arraylist when clicked.
    SectionAdapter adapter;
    ListView lv;

    public SectionFragment(Subject subject) {
        this.subject = subject;
        this.position = MainActivity.subjects.indexOf(subject);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lv != null) {
            adapter = new SectionAdapter(getActivity(), subject, position);
            lv.setAdapter(adapter);
            lv.invalidate();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_section, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_section_item_add) {
            AddDialog dialog = new AddDialog(MainActivity.FRAGMENT_SECTION);
            dialog.show(getFragmentManager(), "Apples");
        } else if (id == R.id.menu_section_item_delete) {

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        //Grab list view, create adapter, set it.
        lv = (ListView)rootView.findViewById(R.id.subjectView);
        adapter = new SectionAdapter(getActivity(), subject, position);
        lv.setAdapter(adapter);

        return rootView;
    }
}