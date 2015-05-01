package edu.auburn.eng.csse.comp3710.team6;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;

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

    DatabaseHelper dbHelper;
    SQLiteDatabase myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        dbHelper = DatabaseHelper.getInstance(getActivity());
        myDB = dbHelper.getWritableDatabase();

        //Grab list view, create adapter, set it.
        ListView lv = (ListView)rootView.findViewById(R.id.subjectView);
//        SectionAdapter adapter = new SectionAdapter(this.getActivity(), subject, position);
//        lv.setAdapter(adapter);
        showList(lv);

        return rootView;
    }

    public ArrayList<Section> getData() {
        // TODO Auto-generated method stub
        String[]columns = new String[]{ dbHelper.TABLE_SECTION_KEY_SECTION, dbHelper.TABLE_SECTIONS_KEY_SUBJECT};
        Cursor c = myDB.query(dbHelper.TABLE_SECTIONS, columns, null, null, null, null, null);
        ArrayList<Section> result = new ArrayList<>();
        int iSectionName = c.getColumnIndex(dbHelper.TABLE_SECTION_KEY_SECTION);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result.add(new Section(c.getString(iSectionName)));
        }
        return result;
    }

    private void showList(ListView lv) {
        ArrayList<Section> data = getData();
        lv.setAdapter(new SectionAdapter(getActivity(), data));
    }
}