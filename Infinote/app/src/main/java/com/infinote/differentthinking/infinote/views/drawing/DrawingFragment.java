package com.infinote.differentthinking.infinote.views.drawing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
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
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.graphics.CanvasView;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.list_notes.ListNotesActivity;
import com.infinote.differentthinking.infinote.views.drawing.base.DrawingContract;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

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
    private AllAngleExpandableButton colorsButton;
    private AllAngleExpandableButton figuresButton;
    private AllAngleExpandableButton strokeButton;
    private AllAngleExpandableButton brushButton;
    private AllAngleExpandableButton modeButton;
    private SeekBar strokeSeekBar;
    private ImageButton saveButton;

    private FloatingActionButton noteSaveButton;
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

        this.colorsButton = (AllAngleExpandableButton) view.findViewById(R.id.button_expandable);
        this.figuresButton = (AllAngleExpandableButton) view.findViewById(R.id.drawer_figures);
        this.brushButton = (AllAngleExpandableButton) view.findViewById(R.id.drawer_strokes);
        this.modeButton = (AllAngleExpandableButton) view.findViewById(R.id.drawer_mode);
        this.strokeSeekBar = (SeekBar) view.findViewById(R.id.stroke_width);
        this.saveButton = (ImageButton) view.findViewById(R.id.save_button);
        this.canvas = (CanvasView) view.findViewById(R.id.canvas);
        final Button monoColorButton = (Button) view.findViewById(R.id.button_monocolor);

        this.strokeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                canvas.setPaintStrokeWidth(progress / 3);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

        this.createModeButton();
        this.createStrokeButton();
        this.createColorsButton();
        this.createFiguresButton();


        this.setupAlertDialog();
        this.canvas.setDrawingCacheEnabled(true);

        this.saveButton.setOnClickListener(new View.OnClickListener() {
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

    private void createModeButton() {
        final List<ButtonData> buttonDatas = new ArrayList<>();

        ButtonData defaultButton = ButtonData.buildIconButton(context, R.mipmap.pen_icon, 0);
        ButtonData eraserButton = ButtonData.buildIconButton(context, R.mipmap.eraser_icon, 0);

        buttonDatas.add(defaultButton);
        buttonDatas.add(eraserButton);

        modeButton.setButtonDatas(buttonDatas);
        modeButton.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                switch (index) {
                    case 1:
                        canvas.setPaintStrokeColor(Color.rgb(0, 0, 0));
                        canvas.setPaintStrokeWidth(3F);
                        break;
                    case 2:
                        canvas.setPaintStrokeColor(canvas.getBaseColor());
                        canvas.setPaintStrokeWidth(24F);
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

    private void createStrokeButton() {
        final List<ButtonData> buttonDatas = new ArrayList<>();

        ButtonData strokeButton = ButtonData.buildIconButton(context, R.mipmap.brush_icon, 0);
        ButtonData fillButton = ButtonData.buildIconButton(context, R.mipmap.fill_icon, 0);
        ButtonData fillAndStrokeButton = ButtonData.buildIconButton(context, R.mipmap.fillandstroke_icon, 0);

        buttonDatas.add(strokeButton);
        buttonDatas.add(fillButton);
        buttonDatas.add(fillAndStrokeButton);

        brushButton.setButtonDatas(buttonDatas);
        brushButton.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                switch (index) {
                    case 1:
                        canvas.setPaintStyle(Paint.Style.STROKE);
                        break;
                    case 2:
                        canvas.setPaintStyle(Paint.Style.FILL);
                        break;
                    case 3:
                        canvas.setPaintStyle(Paint.Style.FILL_AND_STROKE);
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

    private void createFiguresButton() {
        final List<ButtonData> buttonDatas = new ArrayList<>();
        ButtonData penButton = ButtonData.buildIconButton(context, R.mipmap.pencil_icon, 0);
        ButtonData lineButton = ButtonData.buildIconButton(context, R.mipmap.line_icon, 0);
        ButtonData rectangleButton = ButtonData.buildIconButton(context, R.mipmap.rectangle_icon, 0);
        ButtonData circleButton = ButtonData.buildIconButton(context, R.mipmap.circle_icon, 0);
        ButtonData elipseButton = ButtonData.buildIconButton(context, R.mipmap.elipse_icon, 0);
        ButtonData curvedLineButton = ButtonData.buildIconButton(context, R.mipmap.curvedline_icon, 0);
        buttonDatas.add(penButton);
        buttonDatas.add(lineButton);
        buttonDatas.add(rectangleButton);
        buttonDatas.add(circleButton);
        buttonDatas.add(elipseButton);
        buttonDatas.add(curvedLineButton);

        figuresButton.setButtonDatas(buttonDatas);
        figuresButton.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                switch (index) {
                    case 1:
                        canvas.setDrawer(CanvasView.Drawer.PEN);
                        break;
                    case 2:
                        canvas.setDrawer(CanvasView.Drawer.LINE);
                        break;
                    case 3:
                        canvas.setDrawer(CanvasView.Drawer.RECTANGLE);
                        break;
                    case 4:
                        canvas.setDrawer(CanvasView.Drawer.CIRCLE);
                        break;
                    case 5:
                        canvas.setDrawer(CanvasView.Drawer.ELLIPSE);
                        break;
                    case 6:
                        canvas.setDrawer(CanvasView.Drawer.QUADRATIC_BEZIER);
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

    private void createColorsButton() {
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

        colorsButton.setButtonDatas(buttonDatas);
        colorsButton.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
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
