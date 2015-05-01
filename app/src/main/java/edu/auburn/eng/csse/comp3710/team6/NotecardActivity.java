package edu.auburn.eng.csse.comp3710.team6;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;

/**
 * Created by kennystreit on 4/24/15.
 */
public class NotecardActivity extends ActionBarActivity {

    private ArrayList<Note> notecardList = new ArrayList<>();

    DatabaseHelper dbHelper;
    SQLiteDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecard_frag_holder);

        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
        myDB = dbHelper.getWritableDatabase();

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
            Bundle bundle = this.getIntent().getExtras();
            String sectionName = bundle.getString("sectionName");
//            notecardList = MainActivity.subjects.get(subPos).getSections().get(sectionPos).getNoteCards();
            Bundle bundleOutgoing = new Bundle();
            bundleOutgoing.putString("sectionName", sectionName);
            notecardList = getData(sectionName);
            PlaceholderFragment frag = new PlaceholderFragment(notecardList);
            frag.setArguments(bundleOutgoing);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, frag)
                    .commit();
            Log.i("Notecards", "Bundle was null");
            Log.d("KENNY", "Section Name: " + sectionName);
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

            final String sectionName = getArguments().getString("sectionName");

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

            ImageView fab = (ImageView) rootView.findViewById(R.id.floating_action_button);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createNoteDialog(sectionName);
                }
            });
            return rootView;
        }

        private void createNoteDialog(final String sectionName) {

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.notecard_dialog_layout);
            dialog.setTitle("Create Your Notecard:");

            final EditText question = (EditText) dialog.findViewById(R.id.question);
            final EditText answer = (EditText) dialog.findViewById(R.id.answer);
            dialog.findViewById(R.id.save_notecard).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            // create new item from dialog text
                            if (!question.getText().toString().equals("") && !answer.getText().toString().equals("")) {

                                DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
                                SQLiteDatabase myDB = dbHelper.getWritableDatabase();
                                ContentValues values = dbHelper.saveNotecardValues(
                                        question.getText().toString(),
                                        answer.getText().toString(),
                                        sectionName,
                                        MainActivity.currentSub.getSubjectName());

                                long newRowId = myDB.insert(
                                        dbHelper.TABLE_NOTES,
                                        null,
                                        values);

                                Toast.makeText(getActivity(),
                                        "Your notecard was saved!\nRow ID: " + newRowId,
                                        Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getActivity(),
                                        "You must enter a question and/or answer",
                                        Toast.LENGTH_SHORT).show();
                        }
                    });

            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
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

    public ArrayList<Note> getData(String sectionName) {

        String[]columns = new String[]{ dbHelper.TABLE_NOTES_KEY_FRONT,
                dbHelper.TABLE_NOTES_KEY_BACK, dbHelper.TABLE_NOTES_KEY_SECTION, dbHelper.TABLE_NOTES_KEY_SUBJECT};
        Cursor c = myDB.query(dbHelper.TABLE_NOTES, columns, null, null, null, null, null);
        Log.d("KENNY", "Query: " + c.toString());
        ArrayList<Note> result = new ArrayList<>();
        int iFront = c.getColumnIndex(dbHelper.TABLE_NOTES_KEY_FRONT);
        int iBack = c.getColumnIndex(dbHelper.TABLE_NOTES_KEY_BACK);
        int iSection = c.getColumnIndex(dbHelper.TABLE_NOTES_KEY_SECTION);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getString(iSection).equals(sectionName)) {
                result.add(new Note(c.getString(iFront), c.getString(iBack)));
                Toast.makeText(getApplicationContext(), "Front:: " + iFront, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "No section found:: " + sectionName
                        + ", db section: " + c.getString(iSection), Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }
}
