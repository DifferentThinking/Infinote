package com.infinote.differentthinking.infinote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView createAccountView = (TextView) this.findViewById(R.id.tv_no_account);

        createAccountView.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
