package com.infinote.differentthinking.infinote.views.list_notes;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.Drawer;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.auth.login.LoginActivity;
import com.infinote.differentthinking.infinote.views.list_notes.base.ListNotesContract;
import com.infinote.differentthinking.infinote.views.note.NoteActivity;
import com.infinote.differentthinking.infinote.views.profile.ProfileActivity;


public class ListNotesFragment extends Fragment implements ListNotesContract.View {
    private ListNotesContract.Presenter presenter;
    private Context context;
    private InfinoteProgressDialog progressDialog;

    private Button noteSaveButton;

    private Drawer drawer ;

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        at.markushi.ui.CircleButton accountButton = (at.markushi.ui.CircleButton) view.findViewById(R.id.account_button);
        FloatingActionButton newNoteButton = (FloatingActionButton) view.findViewById(R.id.fab);

        newNoteButton.setOnClickListener(
            new FloatingActionButton.OnClickListener(){
                @Override
                public void onClick(View v){
                showNewNoteActivity();
                }
            }
        );

        accountButton.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (presenter.isUserLoggedIn()) {
                        showProfileActivity();
                    }
                    else {
                        showLoginActivity();
                    }
                }
            }
        );

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(ListNotesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setDialog(InfinoteProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public void showProfileActivity() {
        Intent intent = new Intent(this.context, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginActivity() {
        Intent intent = new Intent(this.context, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNewNoteActivity() {
        Intent intent = new Intent(this.context, NoteActivity.class);
        startActivity(intent);
    }
}


