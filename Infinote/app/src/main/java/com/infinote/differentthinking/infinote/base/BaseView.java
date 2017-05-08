package com.infinote.differentthinking.infinote.base;

import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.list_notes.base.ListNotesContract;

public interface BaseView<T> {
    void setPresenter(T presenter);

    void setDialog(InfinoteProgressDialog progressDialog);
}
