package com.infinote.differentthinking.infinote.views.auth.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.list_notes.ListNotesActivity;
import com.infinote.differentthinking.infinote.views.auth.register.base.RegisterContract;


public class RegisterFragment extends Fragment implements RegisterContract.View {
    private TextView alreadyHaveAccountView;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText firstnameEditText;
    private EditText lastnameEditText;

    private RegisterContract.Presenter presenter;
    private InfinoteProgressDialog progressDialog;
    private Context context;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        this.alreadyHaveAccountView = (TextView) view.findViewById(R.id.tv_already_has_account);

        this.emailEditText = (EditText) view.findViewById(R.id.et_email);
        this.passwordEditText = (EditText) view.findViewById(R.id.et_password);
        this.firstnameEditText = (EditText) view.findViewById(R.id.et_first_name);
        this.lastnameEditText = (EditText) view.findViewById(R.id.et_last_name);

        Button registerBtn = (Button) view.findViewById(R.id.btn_register);

        alreadyHaveAccountView.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        presenter.onHasAccountClicked();
                    }
                }
        );

        registerBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.registerUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
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
    public void setDialog(InfinoteProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showListNotesActivity() {
        Intent intent = new Intent(this.context, ListNotesActivity.class);
        startActivity(intent);
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
        Toast.makeText(getContext(), this.firstnameEditText.getText().toString() + getString(R.string.user_registered_notify_message), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG)
                .show();
    }
 }
