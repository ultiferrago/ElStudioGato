package edu.auburn.eng.csse.comp3710.team6;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Ferrago on 5/3/15.
 */
public class AddNoteDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notecard_dialog_layout);
        final EditText question = (EditText) dialog.findViewById(R.id.question);
        final EditText answer = (EditText) dialog.findViewById(R.id.answer);
        dialog.findViewById(R.id.save_notecard).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // create new item from dialog text
                        String questString = question.getText().toString();
                        questString = questString.replaceAll(" ", "");
                        questString = questString.replaceAll("\n", "");

                        String answerString = answer.getText().toString();
                        answerString = answerString.replaceAll(" ", "");
                        answerString = answerString.replaceAll("\n", "");

                        if (!questString.equals("") || !answerString.equals("")) {
                            Note note = new Note();
                            note.setFront(question.getText().toString());
                            note.setBack(answer.getText().toString());
                            NotecardActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).addNote(note);
                            MainActivity.subjects.get(NotecardActivity.subjectPos).getSections().get(NotecardActivity.sectionPos).addNote(note);
                            ((NotecardActivity) getActivity()).refresh();
                            dialog.dismiss();
                        } else
                            Toast.makeText(getActivity(),
                                    "You must enter atleast question or an answer", Toast.LENGTH_SHORT).show();
                    }
                });
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
}
