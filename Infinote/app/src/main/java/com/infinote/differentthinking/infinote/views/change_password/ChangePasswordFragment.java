package com.infinote.differentthinking.infinote.views.change_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.views.change_password.base.ChangePasswordContract;
import com.infinote.differentthinking.infinote.views.list_notes.ListNotesActivity;

public class ChangePasswordFragment extends Fragment implements ChangePasswordContract.View {
    private ChangePasswordContract.Presenter presenter;
    private Context context;

    private com.dd.CircularProgressButton changePasswordButton;
    private EditText changePasswordEditText;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        this.changePasswordEditText = (EditText) view.findViewById(R.id.et_change_password);
        this.changePasswordButton = (com.dd.CircularProgressButton) view.findViewById(R.id.btn_change_password);

        this.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changePasswordForUser(changePasswordEditText.getText().toString());
                changePasswordButton.setProgress(50);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(ChangePasswordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void notifySuccessful(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showListNotesActivity() {
        Intent intent = new Intent(this.context, ListNotesActivity.class);
        startActivity(intent);
    }
}