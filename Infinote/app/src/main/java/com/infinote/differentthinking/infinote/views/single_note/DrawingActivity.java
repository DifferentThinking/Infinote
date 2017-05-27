package com.infinote.differentthinking.infinote.views.single_note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.single_note.base.DrawingContract;

public class DrawingActivity extends AppCompatActivity {
    private DrawingContract.Presenter presenter;
    private InfinoteProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        DrawingFragment noteFragment =
                (DrawingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (noteFragment == null) {
            noteFragment = DrawingFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, noteFragment)
                    .commit();
        }



        this.presenter = new DrawingPresenter(noteFragment, this);
        this.dialog = new InfinoteProgressDialog();
        this.dialog.setContext(this);
        noteFragment.setDialog(this.dialog);
        noteFragment.setPresenter(this.presenter);
    }
}
