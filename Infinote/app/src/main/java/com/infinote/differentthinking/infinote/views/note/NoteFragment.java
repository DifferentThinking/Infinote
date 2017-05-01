package com.infinote.differentthinking.infinote.views.note;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.note.drawer.Drawer;
import com.infinote.differentthinking.infinote.views.note.base.NoteContract;

public class NoteFragment extends Fragment implements NoteContract.View {
    private NoteContract.Presenter presenter;
    private Context context;
    private InfinoteProgressDialog progressDialog;

    private Drawer dv ;
    private Paint paint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        this.setupCanvas();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    public void setPresenter(NoteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setDialog(InfinoteProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    private void setupCanvas() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);

        dv = new Drawer(this.getContext(), paint);
        this.getActivity().setContentView(dv);
    }
}
