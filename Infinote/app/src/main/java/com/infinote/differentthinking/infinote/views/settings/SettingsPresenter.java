package com.infinote.differentthinking.infinote.views.settings;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.views.settings.base.SettingsContract;

public class SettingsPresenter implements SettingsContract.Presenter {
    SettingsContract.View view;
    private UserData userData;

    public SettingsPresenter(SettingsContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

}