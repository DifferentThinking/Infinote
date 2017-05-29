package com.infinote.differentthinking.infinote.views.settings.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface SettingsContract {
    interface View extends BaseView<Presenter> {
        void showDialogForLoading();

        void dismissDialog();
    }

    interface Presenter {
    }
}