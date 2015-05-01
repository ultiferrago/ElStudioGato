package edu.auburn.eng.csse.comp3710.team6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.auburn.eng.csse.comp3710.team6.database.DatabaseHelper;

/**
 * Created by Ferrago on 4/29/15.
 */
public class AddDialog extends DialogFragment {

    private EditText mDialogEditText;
    private String mDialogHint;
    private String mDialogTitle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_layout, null);
        mDialogEditText = (EditText)v.findViewById(R.id.dialog_subjectName);
        if (MainActivity.currentFrag == MainActivity.FRAGMENT_SUBJECT) {
            mDialogHint = getResources().getString(R.string.dialog_add_subject_hint);
            mDialogTitle = getResources().getString(R.string.dialog_add_subject_title);
        } else {
            mDialogHint = getResources().getString(R.string.dialog_add_section_hint);
            mDialogTitle = getResources().getString(R.string.dialog_add_section_title);
        }
        mDialogEditText.setHint(mDialogHint);

        final String subjectName = getArguments().getString("subjectName");

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(mDialogTitle)
                .setPositiveButton(R.string.dialog_create,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Okay so here when the okay button is clicked lets create and move frags.
                                if (MainActivity.currentFrag == MainActivity.FRAGMENT_SUBJECT) {
                                    String subjectName = mDialogEditText.getText().toString();
                                    Subject newSubject = new Subject(subjectName);
//                                    MainActivity.subjects.add(newSubject);
//                                    MainActivity.toSectionFragment(newSubject);

                                    DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
                                    SQLiteDatabase myDB = dbHelper.getWritableDatabase();
                                    ContentValues values = dbHelper.saveSubjectValues(subjectName);

                                    long newRowId = myDB.insert(
                                            dbHelper.TABLE_SUBJECTS,
                                            null,
                                            values);

                                    Toast.makeText(getActivity(), "Row ID: " + newRowId, Toast.LENGTH_SHORT).show();

                                } else {
                                    String sectionName = mDialogEditText.getText().toString();
//                                    Section newSection = new Section(mDialogEditText.getText().toString());
//                                    MainActivity.currentSub.addSection(newSection);

                                    DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
                                    SQLiteDatabase myDB = dbHelper.getWritableDatabase();
                                    ContentValues values = dbHelper.saveSectionValues(sectionName, subjectName);

                                    long newRowId = myDB.insert(
                                            dbHelper.TABLE_SECTIONS,
                                            null,
                                            values);

                                    Toast.makeText(getActivity(), "Row ID: " + newRowId, Toast.LENGTH_SHORT).show();

//                                    Log.i("SectionClick", newSection.getName());
//                                    Intent intent = new Intent(getActivity(), NotecardActivity.class);
//                                    intent.putExtra("SubjectPos", MainActivity.subjects.indexOf(MainActivity.currentSub));
//                                    intent.putExtra("SectionPos", MainActivity.currentSub.getSections().indexOf(newSection));
//
//                                    getActivity().startActivity(intent);
                                }
                            }
                        })
                .create();
    }
}
