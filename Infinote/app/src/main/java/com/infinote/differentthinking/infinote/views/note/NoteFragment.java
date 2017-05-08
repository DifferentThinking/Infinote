package com.infinote.differentthinking.infinote.views.note;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.Drawer;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.ListNotesActivity;
import com.infinote.differentthinking.infinote.views.note.base.NoteContract;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;

public class NoteFragment extends Fragment implements NoteContract.View {
    private NoteContract.Presenter presenter;
    private Context context;
    private InfinoteProgressDialog progressDialog;

    private Button noteSaveButton;

    private Drawer drawer ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        AllAngleExpandableButton button = (AllAngleExpandableButton) view.findViewById(R.id.button_expandable);
        this.noteSaveButton = (Button) view.findViewById(R.id.note_save_button);
        this.drawer = (Drawer) view.findViewById(R.id.note_drawer);


        this.doSomething(button);
        this.drawer.setDrawingCacheEnabled(true);
        this.noteSaveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm = drawer.getDrawingCache();
                byte[] canvasData = getCanvasData(bm);
                String encodedPicture = Base64.encodeToString(canvasData, Base64.DEFAULT);
                presenter.saveNote(encodedPicture);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    public void setPresenter(NoteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setDialog(InfinoteProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public static NoteFragment newInstance() {
        return new NoteFragment();
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

    private void doSomething(AllAngleExpandableButton button) {
        final List<ButtonData> buttonDatas = new ArrayList<>();
//        int drawable = R.drawable.ic_action_red;
//        ButtonData buttonData = ButtonData.buildIconButton(context, drawable[i], 0);
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

        button.setButtonDatas(buttonDatas);
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                Toast.makeText(getActivity(), "clicked index: " + index,
                        Toast.LENGTH_LONG).show();

                switch (index) {
                    case 1:  drawer.setColor(Color.BLACK);
                        break;
                    case 2:  drawer.setColor(Color.rgb(244, 67, 54));
                        break;
                    case 3:  drawer.setColor(Color.rgb(33,150,243));
                        break;
                    case 4:  drawer.setColor(Color.rgb(255, 165, 0));
                        break;
                    case 5:  drawer.setColor(Color.rgb(255,235,59));
                        break;
                    case 6:  drawer.setColor(Color.rgb(3,169,244));
                        break;
                    case 7:  drawer.setColor(Color.rgb(156,39,176));
                        break;
                    case 8:  drawer.setColor(Color.rgb(76,175,80));
                        break;
                    case 9:  drawer.setColor(Color.rgb(139,195,74));
                        break;
                    case 10:  drawer.setColor(Color.rgb(233,30,99));
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
}
