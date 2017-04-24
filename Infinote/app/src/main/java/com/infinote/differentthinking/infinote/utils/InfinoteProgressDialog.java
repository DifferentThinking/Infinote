package com.infinote.differentthinking.infinote.utils;

import android.app.ProgressDialog;
import android.content.Context;


public class InfinoteProgressDialog {

    ProgressDialog dialog;

    public void setContext(Context context) {
        this.dialog = new ProgressDialog(context);
    }

    public void showProgress(String text) {
        this.dialog.setMessage(text);
        this.dialog.show();
    }

    public void dismissProgress() {
        this.dialog.dismiss();
    }
}