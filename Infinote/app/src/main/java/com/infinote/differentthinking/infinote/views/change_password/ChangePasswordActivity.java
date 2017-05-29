package com.infinote.differentthinking.infinote.views.change_password;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.views.change_password.base.ChangePasswordContract;

public class ChangePasswordActivity extends AppCompatActivity {
    private ChangePasswordContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ChangePasswordFragment changePasswordFragment =
                (ChangePasswordFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (changePasswordFragment == null) {
            changePasswordFragment = ChangePasswordFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, changePasswordFragment)
                    .commit();
        }

        this.presenter = new ChangePasswordPresenter(changePasswordFragment, this);
        changePasswordFragment.setPresenter(this.presenter);

    }
}
