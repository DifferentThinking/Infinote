package com.infinote.differentthinking.infinote.views.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.change_password.ChangePasswordActivity;
import com.infinote.differentthinking.infinote.views.profile.ProfileActivity;
import com.infinote.differentthinking.infinote.views.settings.base.SettingsContract;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SettingsFragment extends Fragment implements SettingsContract.View {
    private SettingsContract.Presenter presenter;
    private Context context;
    private InfinoteProgressDialog progressDialog;

    private String profilePictureAsString;

    private de.hdodenhof.circleimageview.CircleImageView profilePicture;
    private TextView changePictureButton;
    private TextView changePasswordButton;
    private ImageButton doneButton;

    private byte[] encodedPicture;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);

        this.changePictureButton = (TextView) view.findViewById(R.id.tv_change_picture);
        this.profilePicture = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_pic_settings);
        this.doneButton = (ImageButton) view.findViewById(R.id.done_button);
        this.changePasswordButton = (TextView) view.findViewById(R.id.tv_change_password);

        this.changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
            }
        });

        this.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveProfilePicture(profilePictureAsString);
            }
        });

        this.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordActivity();
            }
        });

        encodedPicture = this.getActivity().getIntent().getByteArrayExtra("PICTURE");
        if(encodedPicture != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(encodedPicture, 0, encodedPicture.length);
            profilePicture.setImageBitmap(bmp);
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            InputStream inputStream;
            try {
                inputStream = context.getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

            if(encodedPicture != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(encodedPicture, 0, encodedPicture.length);
                profilePicture.setImageBitmap(bmp);
            }
            else {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                profilePicture.setImageBitmap(bmp);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            }

//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            byte[] byteArray = stream.toByteArray();
//            profilePictureAsString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setDialog(InfinoteProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public void showChangePasswordActivity() {
        Intent intent = new Intent(this.context, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifySuccessful(String message) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showProfileActivity() {
        Intent intent = new Intent(this.context, ProfileActivity.class);
        startActivity(intent);
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
