package com.infinote.differentthinking.infinote.views.drawing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.CanvasView;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.list_notes.ListNotesActivity;
import com.infinote.differentthinking.infinote.views.drawing.base.DrawingContract;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrawingFragment extends Fragment implements DrawingContract.View {
    private AlertDialog.Builder alertDialog;
    private DrawingContract.Presenter presenter;
    private Context context;
    private InfinoteProgressDialog progressDialog;
    private Boolean darkMode = false;

    private Button noteSaveButton;
    private Button monoColorButton;

    private CanvasView canvas;
    private boolean editMode = false;
    private String pictureId;

    public static DrawingFragment newInstance() {
        return new DrawingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing, container, false);

        AllAngleExpandableButton colorsButton = (AllAngleExpandableButton) view.findViewById(R.id.button_expandable);

        this.noteSaveButton = (Button) view.findViewById(R.id.note_save_button);
        this.canvas = (CanvasView) view.findViewById(R.id.canvas);

        final FloatingActionButton darkModeButton = (FloatingActionButton) view.findViewById(R.id.button_darkmode);
        final Button monoColorButton = (Button) view.findViewById(R.id.button_monocolor);

        darkModeButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        darkModeButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_darkmode));

        monoColorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isInDarkMode()) {
                    monoColorButton.setBackgroundResource(R.drawable.circlebuttonwhite);
                    canvas.setPaintStrokeColor(Color.WHITE);
                }
                else {
                    monoColorButton.setBackgroundResource(R.drawable.circlebuttonblack);
                    canvas.setPaintStrokeColor(Color.BLACK);
                }
            }
        });


        darkModeButton.setOnClickListener(
                new FloatingActionButton.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if (isInDarkMode()) {
                            canvas.setBaseColor(Color.WHITE);
                            if (canvas.getPaintStrokeColor() == Color.WHITE) {
                                canvas.setPaintStrokeColor(Color.BLACK);
                                monoColorButton.setBackgroundResource(R.drawable.circlebuttonblack);
                            }

                            darkModeButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_darkmode));
                            darkMode = false;
                        }
                        else {
                            canvas.setBaseColor(Color.BLACK);
                            if (canvas.getPaintStrokeColor() == Color.BLACK) {
                                canvas.setPaintStrokeColor(Color.WHITE);
                                monoColorButton.setBackgroundResource(R.drawable.circlebuttonwhite);
                            }
                            darkModeButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_darkmode));
                            darkMode = true;
                        }
                    }
                }
        );

        this.createColorsButton(colorsButton);


        this.setupAlertDialog();
        this.canvas.setDrawingCacheEnabled(true);
        this.noteSaveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        byte[] decodedString = this.getActivity().getIntent().getByteArrayExtra("ENCODED_IMAGE");
        if (decodedString != null) {
            this.editMode = true;
            this.pictureId = this.getActivity().getIntent().getStringExtra("ID");
            Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            this.canvas.drawBitmap(bm);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(DrawingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setDialog(InfinoteProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    private byte[] getCanvasData(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    @Override
    public void showDialogForLoading() {
        this.progressDialog.showProgress("Loading...");
    }

    @Override
    public void dismissDialog() {
        this.progressDialog.dismissProgress();
    }

    @Override
    public void notifySuccessful() {
        Toast.makeText(this.context, getString(R.string.note_saved_successfully), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showListNotesActivity() {
        Intent intent = new Intent(this.context, ListNotesActivity.class);
        startActivity(intent);
    }

    public Boolean isInDarkMode() {
        return darkMode;
    }

    private void createColorsButton(AllAngleExpandableButton button) {
        final List<ButtonData> buttonDatas = new ArrayList<>();
        ButtonData mainButton = ButtonData.buildIconButton(context, R.drawable.ic_action_pallette, 0);
        buttonDatas.add(mainButton);
        int[] colors = {R.color.red,
                       R.color.blue,
                       R.color.orange,
                       R.color.yellow,
                       R.color.light_blue,
                       R.color.purple,
                       R.color.green,
                       R.color.light_green,
                       R.color.pink};

        for (int i = 0; i < colors.length; i++) {
            ButtonData buttonData =  ButtonData.buildTextButton("");
            buttonData.setBackgroundColorId(context, colors[i]);
            buttonDatas.add(buttonData);
        }

        final ButtonData test =  ButtonData.buildIconButton(context, R.drawable.ic_action_circle, 0);
        test.setBackgroundColorId(context, R.color.blue);
        buttonDatas.add(test);

        button.setButtonDatas(buttonDatas);
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                Toast.makeText(getActivity(), "clicked index: " + index,
                        Toast.LENGTH_LONG).show();

                switch (index) {
                    case 1:  canvas.setPaintStrokeColor(Color.BLACK);
                        break;
                    case 2:  canvas.setPaintStrokeColor(Color.rgb(244, 67, 54));
                        break;
                    case 3:  canvas.setPaintStrokeColor(Color.rgb(33,150,243));
                        break;
                    case 4:  canvas.setPaintStrokeColor(Color.rgb(255, 165, 0));
                        break;
                    case 5:  canvas.setPaintStrokeColor(Color.rgb(255,235,59));
                        break;
                    case 6:  canvas.setPaintStrokeColor(Color.rgb(3,169,244));
                        break;
                    case 7:  canvas.setPaintStrokeColor(Color.rgb(156,39,176));
                        break;
                    case 8:  canvas.setPaintStrokeColor(Color.rgb(76,175,80));
                        break;
                    case 9:  canvas.setPaintStrokeColor(Color.rgb(139,195,74));
                        break;
                    case 10:  canvas.setPaintStrokeColor(Color.rgb(233,30,99));
                        break;
                }
            }

            @Override
            public void onExpand() {
            }

            @Override
            public void onCollapse() {

            }
        });
    }

    private void setupAlertDialog() {
        this.alertDialog = new AlertDialog.Builder(this.getActivity());
        alertDialog.setTitle("Save note");
        alertDialog.setMessage("Please enter title");

        final EditText editText = new EditText(this.getContext());

        alertDialog.setView(editText);
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton("Save",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    byte[] canvasData = canvas.getBitmapAsByteArray();
                    String encodedPicture = Base64.encodeToString(canvasData, Base64.DEFAULT);

                    String title = editText.getText().toString();
                    if (title.equals("") || title.length() == 0) {
                       title = "No Title";
                    }

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = new Date();
                    String dateAsString = dateFormat.format(date);

                    if(editMode) {
                        presenter.updateNote(pictureId, encodedPicture, title, dateAsString);
                    }
                    else {
                        if (presenter.isUserLoggedIn()) {
                            presenter.saveNote(encodedPicture, title, dateAsString);
                        }
                        else {
                            presenter.saveNoteLocally(encodedPicture, title, dateAsString);
                        }
                    }
                }
                });

        alertDialog.setNegativeButton("Cancel",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    setupAlertDialog();
                }
            });
    }
}
