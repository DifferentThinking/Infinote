package com.infinote.differentthinking.infinote.views.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.settings.base.SettingsContract;

public class SettingsActivity extends AppCompatActivity {
    private SettingsContract.Presenter presenter;
    private InfinoteProgressDialog dialong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingsFragment settingsFragment =
                (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (settingsFragment == null) {
            settingsFragment = SettingsFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, settingsFragment)
                    .commit();
        }

        this.presenter = new SettingsPresenter(settingsFragment, this);
        this.dialong = new InfinoteProgressDialog();
        this.dialong.setContext(this);
        settingsFragment.setDialog(this.dialong);
        settingsFragment.setPresenter(this.presenter);

    }
}

