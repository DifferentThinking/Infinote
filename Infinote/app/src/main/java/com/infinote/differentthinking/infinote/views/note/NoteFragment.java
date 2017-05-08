package com.infinote.differentthinking.infinote.views.note;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.note.drawer.Drawer;
import com.infinote.differentthinking.infinote.views.note.base.NoteContract;

import java.util.ArrayList;
import java.util.List;

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

        this.noteSaveButton = (Button) view.findViewById(R.id.note_save_button);
        this.drawer = (Drawer) view.findViewById(R.id.note_drawer);

        AllAngleExpandableButton button = (AllAngleExpandableButton) view.findViewById(R.id.button_expandable);
        final List<ButtonData> buttonDatas = new ArrayList<>();
//        int drawable = R.drawable.ic_action_red;
//        ButtonData buttonData = ButtonData.buildIconButton(context, drawable[i], 0);
        for (int i = 0; i < 10; i++) {
            ButtonData buttonData =  ButtonData.buildTextButton("");
//            buttonData.setBackgroundColor(44242);
            buttonDatas.add(buttonData);
        }

        ButtonData test =  ButtonData.buildIconButton(context, R.drawable.ic_action_circle, 0);
//        test.setBackgroundColorId(context, 0x90fae);
        test.setBackgroundColor(0x90fae);
        buttonDatas.add(test);

        button.setButtonDatas(buttonDatas);
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                //do whatever you want,the param index is counted from startAngle to endAngle,
                //the value is from 1 to buttonCount - 1(buttonCount if aebIsSelectionMode=true)
            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });

        this.noteSaveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveNote();
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
}
