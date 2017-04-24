package com.infinote.differentthinking.infinote.views.auth.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.auth.register.base.RegisterContract;

public class RegisterActivity extends AppCompatActivity {
    private RegisterContract.Presenter presenter;
    private InfinoteProgressDialog dialong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterFragment registerFragment =
                (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, registerFragment)
                    .commit();
        }

        this.presenter = new RegisterPresenter(registerFragment, this);

        this.dialong = new InfinoteProgressDialog();
        this.dialong.setContext(this);
        registerFragment.setDialog(this.dialong);
        registerFragment.setPresenter(this.presenter);
    }
}
