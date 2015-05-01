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
public class DeleteDialog extends DialogFragment implements View.OnClickListener {


    private Button cancelButton;
    private Button deleteButton;

    private Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_delete);



        return dialog;
    }

    @Override
    public void onClick(View v) {



    }

}
