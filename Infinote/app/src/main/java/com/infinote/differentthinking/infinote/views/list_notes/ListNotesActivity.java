package com.infinote.differentthinking.infinote.views.list_notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.list_notes.base.ListNotesContract;

public class ListNotesActivity extends AppCompatActivity {
    private ListNotesContract.Presenter presenter;
    private InfinoteProgressDialog dialong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        ListNotesFragment listNotesFragment =
                (ListNotesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (listNotesFragment == null) {
            listNotesFragment = ListNotesFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, listNotesFragment)
                    .commit();
        }



        this.presenter = new ListNotesPresenter(listNotesFragment, this);
        this.dialong = new InfinoteProgressDialog();
        this.dialong.setContext(this);
        listNotesFragment.setDialog(this.dialong);
        listNotesFragment.setPresenter(this.presenter);

    }
}
