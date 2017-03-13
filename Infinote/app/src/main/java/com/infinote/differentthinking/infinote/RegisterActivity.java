package com.infinote.differentthinking.infinote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView alreadyHaveAccountView = (TextView) this.findViewById(R.id.tv_already_has_account);

        alreadyHaveAccountView.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
