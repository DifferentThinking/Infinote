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

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.note.drawer.Drawer;
import com.infinote.differentthinking.infinote.views.note.base.NoteContract;

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
    this.drawer.
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
