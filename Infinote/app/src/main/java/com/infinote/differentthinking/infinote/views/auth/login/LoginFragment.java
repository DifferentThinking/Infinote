package com.infinote.differentthinking.infinote.views.auth.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v8.renderscript.ScriptIntrinsicBLAS;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.list_notes.ListNotesActivity;
import com.infinote.differentthinking.infinote.views.auth.login.base.LoginContract;
import com.infinote.differentthinking.infinote.views.auth.register.RegisterActivity;


public class LoginFragment extends Fragment implements LoginContract.View {
    private TextView createAccountTextView;
    private TextView resetPasswordTextView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CircularProgressButton loginButton;

    private TextView welcomeText;
    private Typeface typeFace;

    private CountDownTimer timer;

    private LoginContract.Presenter presenter;
    private Context context;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.timer = new CountDownTimer(5000, 1000) {
            @ScriptIntrinsicBLAS.Diag
            public void onFinish() {
                loginButton.setProgress(0);
            }
            @Override
            public void onTick(long millisUntilFinished) {

            }
        };

        typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Infinity.ttf");
        welcomeText = (TextView) view.findViewById(R.id.welcome_text);
        welcomeText.setTypeface(typeFace);

        this.createAccountTextView = (TextView) view.findViewById(R.id.tv_signup);
        this.resetPasswordTextView = (TextView) view.findViewById(R.id.tv_forgot_password);

        this.usernameEditText = (EditText) view.findViewById(R.id.et_email);
        this.passwordEditText = (EditText) view.findViewById(R.id.et_password);

        this.loginButton = (CircularProgressButton) view.findViewById(R.id.btn_signin);
        this.loginButton.setIndeterminateProgressMode(true);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginButton.getProgress() == 0) {
                    loginButton.setProgress(50);
                    if (!presenter.validateLoginUser(usernameEditText.getText().toString(), passwordEditText.getText().toString())) {
                        loginButton.setErrorText("Invalid data");
                        loginButton.setProgress(-1);
                        timer.start();
                        return;
                    }
                    presenter.loginUser(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                } else if (loginButton.getProgress() == 100) {
                    loginButton.setProgress(0);
                } else {
                    loginButton.setProgress(100);
                }
            }
        });

        this.createAccountTextView.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    presenter.onCreateAccountClicked();
                }
            }
        );

        this.resetPasswordTextView.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        // TO DO
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
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showListNotesActivity() {
        Intent intent = new Intent(this.context, ListNotesActivity.class);
        startActivity(intent);
    }

    @Override
    public void showDialogForLoading() {
        loginButton.setProgress(50);
    }

    @Override
    public void notifySuccessful() {
        Toast.makeText(this.context, getString(R.string.user_loggedin_notify_message), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showRegisterActivty() {
        Intent intent = new Intent(this.context, RegisterActivity.class);
        startActivity(intent);
    }
}
