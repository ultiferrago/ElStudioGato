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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ferrago on 4/29/15.
 */
public class DeleteSubjectDialog extends DialogFragment implements View.OnClickListener {

    private final String SELECTED_ARRAY = "SELECTED";


    private Button cancel;
    private Button delete;

    private Dialog dialog;

    SubjectDeleteAdapter dl;

    private SubjectFragment frag;
    private ConfirmSubjectDeleteDialog cdd = null;

    @Override
    public void onCreate(Bundle resume) {
        super.onCreate(resume);
        if (resume != null) {
            if (dl == null) {
                dl = new SubjectDeleteAdapter(getActivity(), MainActivity.subjects);
            }
            dl.setSelected(resume.getIntegerArrayList(SELECTED_ARRAY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle save) {
        super.onSaveInstanceState(save);
        save.putIntegerArrayList(SELECTED_ARRAY, dl.getSelected());
    }

    public DeleteSubjectDialog() {

    }


    public void setFrag(SubjectFragment frag) {
        this.frag = frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_delete);

        ListView lv = (ListView)dialog.findViewById(R.id.custom_delete_dialog_listview);
        if (dl == null) {
            dl = new SubjectDeleteAdapter(getActivity(), MainActivity.subjects);
        }
        lv.setAdapter(dl);

        cancel = (Button)dialog.findViewById(R.id.custom_delete_dialog_cancel);
        cancel.setOnClickListener(this);
        delete = (Button)dialog.findViewById(R.id.custom_delete_dialog_delete);
        delete.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.custom_delete_dialog_cancel) {
            dialog.dismiss();
            findFrag();
            if (frag != null) {
                frag.redrawList();
            }
            return;
        } else if (v.getId() == R.id.custom_delete_dialog_delete) {

            if (dl.getSelected().isEmpty()) {
                Toast.makeText(getActivity(), "Please select something to delete", Toast.LENGTH_SHORT).show();
            } else {
                dialog.dismiss();
                cdd = new ConfirmSubjectDeleteDialog();
                cdd.setSelected(dl.getSelected());
                cdd.show(getFragmentManager(), "pie");
            }
        }


    }

    public void confirmDelete() {
        ArrayList<Subject> deleteMe = new ArrayList<>();
        for (int i : dl.getSelected()) {
            deleteMe.add(MainActivity.subjects.get(i));
        }

        for (Subject sub : deleteMe) {
            MainActivity.subjects.remove(sub);
        }
        frag.redrawList();
    }

    public void findFrag() {
        if (frag != null) {
            return;
        } else {
            for (Fragment fragIn : getActivity().getSupportFragmentManager().getFragments()) {
                if (fragIn instanceof SubjectFragment) {
                    frag = (SubjectFragment)fragIn;
                    return;
                }
            }
        }
    }


}
