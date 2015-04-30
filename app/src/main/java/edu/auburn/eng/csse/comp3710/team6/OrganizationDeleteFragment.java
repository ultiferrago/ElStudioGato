package edu.auburn.eng.csse.comp3710.team6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Ferrago on 4/29/15.
 */
public class OrganizationDeleteFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.delete_layout, container, false);

        //Grab list view, create adapter, set it.
        ListView lv = (ListView)rootView.findViewById(R.id.delete_layout_list);
        if (MainActivity.currentFrag == MainActivity.FRAGMENT_SUBJECT) {
            SubjectDeleteAdapter adapter = new SubjectDeleteAdapter(getActivity(), this);
            lv.setAdapter(adapter);
        } else {

        }

        Button cancel = (Button) rootView.findViewById(R.id.delete_layout_cancel_button);
        Button delete = (Button) rootView.findViewById(R.id.delete_layout_delete_button);

        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);

        return rootView;
    }

    public void toggleDeleteItem(int position) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delete_layout_cancel_button) {
            //return to last frag.
        } else if (v.getId() == R.id.delete_layout_delete_button) {
            //Delete and return.
        }
    }
}
