package com.infinote.differentthinking.infinote.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.views.drawing.DrawingFragment;
import com.infinote.differentthinking.infinote.views.drawing.base.DrawingContract;

public class TextPopup extends DialogFragment {

    private LayoutInflater inflater;
    private EditText textInput;
    private View view;
    private DrawingContract.View parent;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.custom_text_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setPositiveButton("Place", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textInput = (EditText) view.findViewById(R.id.et_textInput);
                parent.setCanvasText(textInput.getText().toString());

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public void setParentView(DrawingContract.View v) {
        this.parent = v;
    }
}
