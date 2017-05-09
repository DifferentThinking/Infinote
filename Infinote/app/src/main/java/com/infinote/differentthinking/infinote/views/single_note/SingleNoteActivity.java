package com.infinote.differentthinking.infinote.views.single_note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.single_note.base.SingleNoteContract;

public class SingleNoteActivity extends AppCompatActivity {
    private SingleNoteContract.Presenter presenter;
    private InfinoteProgressDialog dialong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        SingleNoteFragment noteFragment =
                (SingleNoteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (noteFragment == null) {
            noteFragment = SingleNoteFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, noteFragment)
                    .commit();
        }



        this.presenter = new SingleNotePresenter(noteFragment, this);
        this.dialong = new InfinoteProgressDialog();
        this.dialong.setContext(this);
        noteFragment.setDialog(this.dialong);
        noteFragment.setPresenter(this.presenter);

    }
}
