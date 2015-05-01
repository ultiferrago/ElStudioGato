package edu.auburn.eng.csse.comp3710.team6;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ferrago on 4/29/15.
 */
public class AddDialog extends DialogFragment implements View.OnClickListener {

        private EditText mDialogEditText;
        private String mDialogHint;
        private String mDialogTitle;

        private Button cancelButton;
        private Button okayButton;

        private Dialog dialog;

        private final String state;

        public AddDialog(String state) {
            this.state = state;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialog_add);
            //Now we need a custom
            mDialogEditText = (EditText)dialog.findViewById(R.id.custom_dialog_edit_text);
            if (state == MainActivity.FRAGMENT_SUBJECT) {
                mDialogHint = getResources().getString(R.string.dialog_add_subject_hint);
                mDialogTitle = getResources().getString(R.string.dialog_add_subject_title);
            } else {
                mDialogHint = getResources().getString(R.string.dialog_add_section_hint);
                mDialogTitle = getResources().getString(R.string.dialog_add_section_title);
            }
            mDialogEditText.setHint(mDialogHint);

            TextView title = (TextView)dialog.findViewById(R.id.custom_dialog_title);
            title.setText(mDialogTitle);

            cancelButton = (Button)dialog.findViewById(R.id.custom_dialog_cancel_button);
            cancelButton.setOnClickListener(this);
            okayButton = (Button)dialog.findViewById(R.id.custom_dialog_create_button);
            okayButton.setOnClickListener(this);
            return dialog;
        }

        @Override
        public void onClick(View v) {
                if (v.getId() == cancelButton.getId()) {
                    dialog.dismiss();
                } else if (v.getId() == okayButton.getId()) {
                    dialog.dismiss();
                    if (state == MainActivity.FRAGMENT_SUBJECT) {
                        if (mDialogEditText.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Please enter a valid name!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Subject sub = new Subject(mDialogEditText.getText().toString());
                        MainActivity.subjects.add(sub);
                        //Now transition into the new frag
                        if (getActivity() instanceof MainActivity) {
                            //Make sure we won't have a cast issue

                            ((MainActivity)getActivity()).toSectionFragment(sub);
                        } else {
                            Toast error = Toast.makeText(getActivity(), "Error! getActivity() didn't work :(", Toast.LENGTH_SHORT);
                            error.show();
                        }

                    } else {
                        dialog.dismiss();
                        if (mDialogEditText.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Please enter a valid name!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //In section fragment.
                        Section sec = new Section(mDialogEditText.getText().toString());
                        MainActivity.currentSub.addSection(sec);
                        ((MainActivity)getActivity()).toNoteActivity(sec);
                    }
                }

        }

}
