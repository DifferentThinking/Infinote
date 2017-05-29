package com.infinote.differentthinking.infinote.views.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.models.base.UserContract;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.list_notes.ListNotesActivity;
import com.infinote.differentthinking.infinote.views.profile.base.ProfileContract;
import com.infinote.differentthinking.infinote.views.settings.SettingsActivity;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    private ProfileContract.Presenter presenter;
    private Context context;
    private InfinoteProgressDialog progressDialog;

    private de.hdodenhof.circleimageview.CircleImageView profileImage;
    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView fullNameTextView;
    private ImageButton settingsButton;
    private CircularProgressButton logoutButton;

    private byte[] decodedString;

    private Typeface usernameTypeFace;
    private Typeface detailsTypeFace;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        this.usernameTypeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/ChampagneBold.ttf");
        this.detailsTypeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Champagne.ttf");

        this.profileImage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_image);
        this.usernameTextView = (TextView) view.findViewById(R.id.tv_profile_username);
        this.emailTextView = (TextView) view.findViewById(R.id.tv_profile_email);
        this.fullNameTextView = (TextView) view.findViewById(R.id.tv_profile_fullname);
        this.logoutButton = (CircularProgressButton) view.findViewById(R.id.logout_button);
        this.settingsButton = (ImageButton) view.findViewById(R.id.settings);

        this.usernameTextView.setTypeface(usernameTypeFace);
        this.fullNameTextView.setTypeface(detailsTypeFace);
        this.emailTextView.setTypeface(detailsTypeFace);

        this.logoutButton.setIndeterminateProgressMode(true);
        this.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logoutButton.getProgress() == 0) {
                    logoutButton.setProgress(50);
                    presenter.logoutUser();
                    showListNotesActivity();
                } else if (logoutButton.getProgress() == 100) {
                    logoutButton.setProgress(0);
                } else {
                    logoutButton.setProgress(100);
                }
            }
        });

        this.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsActivity();
            }
        });

        this.presenter.getInfoForCurrentUser();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setDialog(InfinoteProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public void notifyLogout() {
        Toast.makeText(getContext(), "You have logged out successfully.", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showListNotesActivity() {
        Intent intent = new Intent(this.context, ListNotesActivity.class);
        intent.putExtra("PICTURE", decodedString);
        startActivity(intent);
    }

    @Override
    public void showSettingsActivity() {
        Intent intent = new Intent(this.context, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void setupProfile(UserContract user) {
        String fullName = user.getFirstname() + " " + user.getLastname();
        this.usernameTextView.setText(user.getUsername());
        this.emailTextView.setText(user.getEmail());
        this.fullNameTextView.setText(fullName);

        if (user.getProfile() != null) {
            byte[] decodedString = Base64.decode(user.getProfile(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            this.profileImage.setImageBitmap(bmp);
            this.decodedString = decodedString;
        }
    }

    @Override
    public void showDialogForLoading() {
        this.progressDialog.showProgress("Loading...");
    }

    @Override
    public void dismissDialog() {
        this.progressDialog.dismissProgress();
    }
}