package com.infinote.differentthinking.infinote.views.auth.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.auth.login.base.LoginContract;

public class LoginActivity extends AppCompatActivity {
    private LoginContract.Presenter presenter;
    private InfinoteProgressDialog dialong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, loginFragment)
                    .commit();
        }

        this.presenter = new LoginPresenter(loginFragment, this);
        this.dialong = new InfinoteProgressDialog();
        this.dialong.setContext(this);
        loginFragment.setDialog(this.dialong);
        loginFragment.setPresenter(this.presenter);
    }
}