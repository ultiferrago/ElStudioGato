package edu.auburn.eng.csse.comp3710.team6;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ferrago on 4/29/15.
 */
public class AddSubjectDialog extends DialogFragment implements View.OnClickListener {

    private EditText mDialogEditText;
    private String mDialogHint;
    private String mDialogTitle;

    private Button cancelButton;
    private Button okayButton;

    private Dialog dialog;

    private MainActivity.ElGatoFragment frag = null;


    public AddSubjectDialog() {

    }

    public void setFrag(MainActivity.ElGatoFragment frag) {
        this.frag = frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_add);
        mDialogEditText = (EditText) dialog.findViewById(R.id.custom_dialog_edit_text);
        mDialogHint = getResources().getString(R.string.dialog_add_subject_hint);
        mDialogTitle = getResources().getString(R.string.dialog_add_subject_title);
        mDialogEditText.setHint(mDialogHint);
        TextView title = (TextView) dialog.findViewById(R.id.custom_dialog_title);
        title.setText(mDialogTitle);
        cancelButton = (Button) dialog.findViewById(R.id.custom_dialog_cancel_button);
        cancelButton.setOnClickListener(this);
        okayButton = (Button) dialog.findViewById(R.id.custom_dialog_create_button);
        okayButton.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == cancelButton.getId()) {
            dialog.dismiss();
        } else if (v.getId() == okayButton.getId()) {
            dialog.dismiss();
            if (mDialogEditText.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please enter a valid name!", Toast.LENGTH_SHORT).show();
                return;
            }
            Subject sub = new Subject(mDialogEditText.getText().toString());
            MainActivity.subjects.add(sub);
            findFrag();
            if (frag != null) {
                frag.redrawList();
            }
        }
    }

    public void findFrag() {
        if (frag != null) {
            return;
        } else {
            for (Fragment frag : getActivity().getSupportFragmentManager().getFragments()) {
                if (frag instanceof SubjectFragment) {
                    this.frag = (SubjectFragment) frag;
                    return;
                }
            }
        }
    }

}
