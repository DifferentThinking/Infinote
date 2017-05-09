package com.infinote.differentthinking.infinote.views.list_notes;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.models.base.NoteContract;
import com.infinote.differentthinking.infinote.utils.Drawer;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.auth.login.LoginActivity;
import com.infinote.differentthinking.infinote.views.list_notes.base.ListNotesContract;
import com.infinote.differentthinking.infinote.views.single_note.SingleNoteActivity;
import com.infinote.differentthinking.infinote.views.profile.ProfileActivity;

import java.util.List;


public class ListNotesFragment extends Fragment implements ListNotesContract.View {
    private ListNotesContract.Presenter presenter;
    private Context context;
    private InfinoteProgressDialog progressDialog;
    private ListView listViewNotes;
    private ProgressBar loadingPanel;

    private Button noteSaveButton;

    private Drawer drawer ;

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        this.listViewNotes = (ListView) view.findViewById(R.id.lv_notes);
        this.loadingPanel = (ProgressBar) view.findViewById(R.id.loading_panel);
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

        if (presenter.isUserLoggedIn()) {
           presenter.getNotesForCurrentUser();
        }

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
        Intent intent = new Intent(this.context, SingleNoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNewNoteActivityWithImage(byte[] encodedImage) {
        Intent intent = new Intent(this.context, SingleNoteActivity.class);

        intent.putExtra("ENCODED_IMAGE", encodedImage);

        startActivity(intent);
    }

    @Override
    public void notifySuccessful() {
        Toast.makeText(getContext(), "Info loaded successfully.", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void hideLoadingPanel() {
        this.loadingPanel.setVisibility(View.GONE);
    }

    public void setupNotesAdapter(final List<? extends NoteContract> notes) {
        ArrayAdapter<NoteContract> noteAdapter = new ArrayAdapter<NoteContract>(this.getContext(), -1, (List<NoteContract>) notes) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(this.getContext());
                    view = inflater.inflate(R.layout.single_note, parent, false);
                }

                ImageView imagePreview = (ImageView) view.findViewById(R.id.note_image_preview);
                TextView noteTitle = (TextView) view.findViewById(R.id.tv_note_title);
                Button noteDeleteButton = (Button) view.findViewById(R.id.note_delete_button);
                Button noteEditButton = (Button) view.findViewById(R.id.note_edit_button);

                String encodedImage = notes.get(position).getPicture();
                final byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imagePreview.setImageBitmap(bm);

                noteEditButton.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNewNoteActivityWithImage(decodedString);
                    }
                });

//                TextView tvTitle = (TextView) view.findViewById(R.id.user_list_title);
//
//                tvTitle.setText(this.getItem(position));

                return view;
            }
        };

        this.listViewNotes.setAdapter(noteAdapter);
    }
}


