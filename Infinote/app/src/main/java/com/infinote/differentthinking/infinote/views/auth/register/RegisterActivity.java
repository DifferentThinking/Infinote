package com.infinote.differentthinking.infinote.views.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.infinote.differentthinking.infinote.R;
import com.infinote.differentthinking.infinote.data.UserData;
import com.infinote.differentthinking.infinote.models.IUser;
import com.infinote.differentthinking.infinote.utils.InfinoteProgressDialog;
import com.infinote.differentthinking.infinote.views.auth.login.LoginActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    private UserData userData;
    private InfinoteProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView alreadyHaveAccountView = (TextView) this.findViewById(R.id.tv_already_has_account);
        Button registerBtn = (Button) this.findViewById(R.id.btn_register);
        final TextView emailTextView = (TextView) this.findViewById(R.id.et_email);
        final TextView passwordTextView = (TextView) this.findViewById(R.id.et_password);
        TextView firstnameTextView = (TextView) this.findViewById(R.id.et_first_name);
        TextView lastnameTextView = (TextView) this.findViewById(R.id.et_last_name);
        this.dialog.setContext(this.getApplicationContext());


        userData = new UserData(this.getApplicationContext());
        alreadyHaveAccountView.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );

        registerBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData.signUp(emailTextView.getText().toString(), passwordTextView.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Observer<IUser>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        dialog.showProgress("Loading...");
                                    }

                                    @Override
                                    public void onNext(IUser value) {
                                        dialog.dismissProgress();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        dialog.dismissProgress();
                                    }

                                    @Override
                                    public void onComplete() {
                                        dialog.dismissProgress();
                                    }
                                });
            }
        });
    }
}
