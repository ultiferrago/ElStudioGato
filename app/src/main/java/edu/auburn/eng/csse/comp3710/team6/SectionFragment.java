package edu.auburn.eng.csse.comp3710.team6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A placehold fragment containing a simple list view.
 */
public class SectionFragment extends Fragment {
    Subject subject; //What subject are we inside of.
    private int position; //What position was this subject in the arraylist when clicked.

    public SectionFragment(Subject subject) {
        this.subject = subject;
        this.position = MainActivity.subjects.indexOf(subject);
        MainActivity.currentFrag = MainActivity.FRAGMENT_SECTION;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        //Grab list view, create adapter, set it.
        ListView lv = (ListView)rootView.findViewById(R.id.subjectView);
        SectionAdapter adapter = new SectionAdapter(this.getActivity(), subject, position);
        lv.setAdapter(adapter);

        return rootView;
    }
}