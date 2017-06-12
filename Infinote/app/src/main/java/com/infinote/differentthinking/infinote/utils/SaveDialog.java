package com.infinote.differentthinking.infinote.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.graphics.CanvasView;
import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.views.drawing.base.DrawingContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveDialog extends DialogFragment {
    private EditText textInput;
    private View view;
    private DrawingContract.View parent;
    private CanvasView canvas;
    private boolean editMode;
    private String pictureId;
    private DrawingContract.Presenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.custom_title_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textInput = (EditText) view.findViewById(R.id.et_textInput);

                byte[] canvasData = canvas.getBitmapAsByteArray();
                String encodedPicture = Base64.encodeToString(canvasData, Base64.DEFAULT);

                String title = textInput.getText().toString();
                if (title.equals("") || title.length() == 0) {
                    title = "No Title";
                }

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date = new Date();
                String dateAsString = dateFormat.format(date);

                if (editMode) {
                    presenter.updateNote(pictureId, encodedPicture, title, dateAsString);
                } else {
                    if (presenter.isUserLoggedIn()) {
                        presenter.saveNote(encodedPicture, title, dateAsString);
                    } else {
                        presenter.saveNoteLocally(encodedPicture, title, dateAsString);
                    }
                }

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

    public void setCanvas(CanvasView canvas) {
        this.canvas = canvas;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public void setPresenter(DrawingContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
