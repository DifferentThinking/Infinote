package com.infinote.differentthinking.infinote.views.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.profile.base.ProfileContract;

public class ProfileActivity extends AppCompatActivity {
    private ProfileContract.Presenter presenter;
    private InfinoteProgressDialog dialong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileFragment profileFragment =
                (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, profileFragment)
                    .commit();
        }



        this.presenter = new ProfilePresenter(profileFragment, this);
        this.dialong = new InfinoteProgressDialog();
        this.dialong.setContext(this);
        profileFragment.setDialog(this.dialong);
        profileFragment.setPresenter(this.presenter);

    }
}

