package com.infinote.differentthinking.infinote.views.list_notes;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.models.base.NoteContract;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.auth.login.LoginActivity;
import com.infinote.differentthinking.infinote.views.list_notes.base.ListNotesContract;
import com.infinote.differentthinking.infinote.views.drawing.DrawingActivity;
import com.infinote.differentthinking.infinote.views.profile.ProfileActivity;

import java.util.List;


public class ListNotesFragment extends Fragment implements ListNotesContract.View {
    private ListNotesContract.Presenter presenter;
    private Context context;
    private InfinoteProgressDialog progressDialog;
    ArrayAdapter<NoteContract> noteAdapter;

    private ListView listViewNotes;
    private at.markushi.ui.CircleButton accountButton;
    private de.hdodenhof.circleimageview.CircleImageView imagePreview;
    private FloatingActionButton newNoteButton;
    private ProgressBar loadingPanel;

    private TextView infinotesText;

    private Typeface infinotesTypeFace;
    private Typeface noteTextTypeFace;

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        this.listViewNotes = (ListView) view.findViewById(R.id.lv_notes);
        this.loadingPanel = (ProgressBar) view.findViewById(R.id.loading_panel);
        this.accountButton = (at.markushi.ui.CircleButton) view.findViewById(R.id.account_button);
        this.newNoteButton = (FloatingActionButton) view.findViewById(R.id.new_note_button);
        this.infinotesText = (TextView) view.findViewById(R.id.infinotes_text);

        this.infinotesTypeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/ChampagneBold.ttf");

        this.infinotesText.setTypeface(infinotesTypeFace);

        newNoteButton.setOnClickListener(
            new FloatingActionButton.OnClickListener(){
                @Override
                public void onClick(View v){
                    showDrawerActivity();
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
        else {
            presenter.getNotesLocally();
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public ArrayAdapter<NoteContract> getNoteAdapter() {
        return this.noteAdapter;
    }

    @Override
    public void setPresenter(ListNotesContract.Presenter presenter) {
        this.presenter = presenter;
    }

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
    public void showDrawerActivity() {
        Intent intent = new Intent(this.context, DrawingActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNewNoteActivityWithImage(String id, byte[] encodedImage, String title) {
        Intent intent = new Intent(this.context, DrawingActivity.class);

        intent.putExtra("ENCODED_IMAGE", encodedImage);
        intent.putExtra("ID", id);
        intent.putExtra("TITLE", title);

        startActivity(intent);
    }

    @Override
    public void showDialogForDeleteingNote() {
        this.progressDialog.showProgress("Deleting note...");
    }

    @Override
    public void dismissDialog() {
        this.progressDialog.dismissProgress();
    }

    @Override
    public void notifySuccessful() {
        Toast.makeText(getContext(), "Note deleted", Toast.LENGTH_SHORT)
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
        this.noteAdapter = new ArrayAdapter<NoteContract>(this.getContext(), -1, (List<NoteContract>) notes) {
            @NonNull
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(this.getContext());
                    view = inflater.inflate(R.layout.single_note, parent, false);
                }

                imagePreview = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.note_image_preview);
                TextView noteDate = (TextView) view.findViewById(R.id.tv_note_date);
                TextView noteTitle = (TextView) view.findViewById(R.id.tv_note_title);
                final ImageButton deleteNoteButton = (ImageButton) view.findViewById(R.id.note_delete_button);
                noteTextTypeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Champagne.ttf");
                noteTitle.setTypeface(noteTextTypeFace);

                String encodedImage = notes.get(position).getPicture();
                final byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imagePreview.setImageBitmap(bm);

                view.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNewNoteActivityWithImage(notes.get(position).get_id(), decodedString, notes.get(position).getTitle());
                    }
                });

                deleteNoteButton.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.deleteNoteById(notes.get(position).get_id());
                        noteAdapter.remove(noteAdapter.getItem(position));
                        noteAdapter.notifyDataSetChanged();
                    }
                });

                noteDate.setText(notes.get(position).getDate());
                noteTitle.setText(notes.get(position).getTitle());

                return view;
            }
        };

        this.listViewNotes.setAdapter(noteAdapter);
    }
}


