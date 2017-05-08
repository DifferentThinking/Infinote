package com.infinote.differentthinking.infinote.utils.base;

import android.view.MotionEvent;

public interface DrawerContract {
    void clearCanvas();

    boolean onTouchEvent(MotionEvent event);
}
