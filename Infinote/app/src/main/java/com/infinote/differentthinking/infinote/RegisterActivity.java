package com.infinote.differentthinking.infinote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.infinote.differentthinking.infinote.data.UserData;

public class RegisterActivity extends AppCompatActivity {
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView alreadyHaveAccountView = (TextView) this.findViewById(R.id.tv_already_has_account);

        userData = new UserData(this.getApplicationContext());
        alreadyHaveAccountView.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        userData.signUp("Pencho", "secret");
                        //Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        //startActivity(intent);
                    }
                }
        );
    }
}
