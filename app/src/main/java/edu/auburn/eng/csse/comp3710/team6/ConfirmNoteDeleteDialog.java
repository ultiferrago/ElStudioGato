package edu.auburn.eng.csse.comp3710.team6;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Ferrago on 5/2/15.
 */
public class ConfirmNoteDeleteDialog extends DialogFragment implements View.OnClickListener {
    Dialog dialog;

    Button cancel;
    Button delete;

    ArrayList<Integer> selected = new ArrayList();

    public ConfirmNoteDeleteDialog() {

    }

    public void setSelected(ArrayList<Integer> ints) {
        selected = (ArrayList<Integer>) ints.clone();
    }

    @Override
    public void onCreate(Bundle resume) {
        super.onCreate(resume);
        if (resume != null) {
            selected = resume.getIntegerArrayList("Selected");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle save) {
        super.onSaveInstanceState(save);
        save.putIntegerArrayList("Selected", selected);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_confirm_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        cancel = (Button) dialog.findViewById(R.id.delete_confirm_cancel);
        cancel.setOnClickListener(this);
        delete = (Button) dialog.findViewById(R.id.delete_confirm_confirm);
        delete.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.delete_confirm_cancel) {
            dialog.dismiss();
        } else if (view.getId() == R.id.delete_confirm_confirm) {
            dialog.dismiss();
            confirmDelete();
        }
    }

    public void confirmDelete() {
        ArrayList<Note> deleteMeNote = new ArrayList<>();
        ArrayList<Note> deleteMeMain = new ArrayList<>();
        for (int i : selected) {
            deleteMeMain.add(MainActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards().get(i));
            deleteMeNote.add(NotecardActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards().get(i));
        }

        for (Note note : deleteMeMain) {
            MainActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards().remove(note);

        }
        for (Note note : deleteMeNote) {
            NotecardActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).getNoteCards().remove(note);
        }

        for (Fragment frag : getActivity().getSupportFragmentManager().getFragments()) {
            if (frag instanceof NotecardActivity.PlaceholderFragment) {
                ((NotecardActivity.PlaceholderFragment) frag).refreshContent();
            }
        }

    }
}
