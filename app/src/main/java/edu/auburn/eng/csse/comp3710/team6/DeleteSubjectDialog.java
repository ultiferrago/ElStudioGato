package edu.auburn.eng.csse.comp3710.team6;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ferrago on 4/29/15.
 */
public class DeleteSubjectDialog extends DialogFragment implements View.OnClickListener {


    private Button cancelButton;
    private Button deleteButton;

    private Dialog dialog;

    SubjectDeleteAdapter dl;

    private Fragment frag;
    public DeleteSubjectDialog(Fragment frag) {
        this.frag = frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_delete);

        ListView lv = (ListView)dialog.findViewById(R.id.custom_delete_dialog_listview);
        dl = new SubjectDeleteAdapter(getActivity(), MainActivity.subjects);
        lv.setAdapter(dl);

        Button cancel = (Button)dialog.findViewById(R.id.custom_delete_dialog_cancel);
        cancel.setOnClickListener(this);
        Button delete = (Button)dialog.findViewById(R.id.custom_delete_dialog_delete);
        delete.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.custom_delete_dialog_cancel) {
            dialog.dismiss();
            return;
        } else if (v.getId() == R.id.custom_delete_dialog_delete) {
            ArrayList<Subject> deleteMe = new ArrayList<>();
            for (int i : dl.getSelected()) {
                deleteMe.add(MainActivity.subjects.get(i));
            }

            for (Subject sub : deleteMe) {
                MainActivity.subjects.remove(sub);
            }
            deleteMe = null;
            dialog.dismiss();
        }


    }

}
