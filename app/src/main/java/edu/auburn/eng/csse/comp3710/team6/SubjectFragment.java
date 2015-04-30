package edu.auburn.eng.csse.comp3710.team6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple list view.
 */
public class SubjectFragment extends Fragment {
    public SubjectFragment() {
        MainActivity.currentFrag = MainActivity.FRAGMENT_SUBJECT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        //Get the list view, create an adapter and set it.
        ListView lv = (ListView)rootView.findViewById(R.id.subjectView);
        SubjectAdapter adapter = new SubjectAdapter(this.getActivity(), MainActivity.subjects);
        lv.setAdapter(adapter);

        return rootView;
    }
}
