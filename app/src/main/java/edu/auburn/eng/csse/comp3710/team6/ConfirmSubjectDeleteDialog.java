package edu.auburn.eng.csse.comp3710.team6;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Ferrago on 5/2/15.
 */
public class ConfirmSubjectDeleteDialog extends DialogFragment implements View.OnClickListener{
     DialogFragment df;
     Dialog dialog;
     public ConfirmSubjectDeleteDialog() {

     }

    public void setDialogFragment(DialogFragment df) {
       this.df = df;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_confirm_layout);

        Button cancel = (Button)dialog.findViewById(R.id.delete_confirm_cancel);
        cancel.setOnClickListener(this);
        Button delete = (Button)dialog.findViewById(R.id.delete_confirm_confirm);
        delete.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.delete_confirm_cancel) {
            dialog.dismiss();
        } else if (view.getId() == R.id.delete_confirm_confirm) {
            if (df instanceof DeleteSubjectDialog) {
                dialog.dismiss();
                ((DeleteSubjectDialog)df).confirmDelete();
            }
        }
    }
}
