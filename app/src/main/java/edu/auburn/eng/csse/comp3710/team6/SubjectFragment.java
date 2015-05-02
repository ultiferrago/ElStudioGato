package edu.auburn.eng.csse.comp3710.team6;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;

/**
 * A placeholder fragment containing a simple list view.
 */
public class SubjectFragment extends Fragment {
    public SubjectFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_subject, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_subject_item_add) {
            AddDialog dialog = new AddDialog(MainActivity.FRAGMENT_SUBJECT);
            dialog.show(getFragmentManager(), "Apples");
        } else if (id == R.id.menu_subject_item_delete) {

        }


        return super.onOptionsItemSelected(item);
    }

    DatabaseHelper dbHelper;
    SQLiteDatabase myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        dbHelper = DatabaseHelper.getInstance(getActivity());
        myDB = dbHelper.getWritableDatabase();

        //Get the list view, create an adapter and set it.
        ListView lv = (ListView)rootView.findViewById(R.id.subjectView);
//        SubjectAdapter adapter = new SubjectAdapter(this.getActivity(), MainActivity.subjects);
//        lv.setAdapter(adapter);
        showList(lv);

        return rootView;
    }

    public ArrayList<Subject> getData() {
        // TODO Auto-generated method stub
        String[]columns = new String[]{ dbHelper.TABLE_SUBJECTS_KEY_NAME};
        Cursor c = myDB.query(dbHelper.TABLE_SUBJECTS, columns, null, null, null, null, null);
        ArrayList<Subject> result = new ArrayList<>();
        int iName = c.getColumnIndex(dbHelper.TABLE_SUBJECTS_KEY_NAME);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result.add(new Subject(c.getString(iName)));
        }
        return result;
    }

    private void showList(ListView lv) {
        ArrayList<Subject> data = getData();
        lv.setAdapter(new SubjectAdapter(getActivity(), data));
    }
}
