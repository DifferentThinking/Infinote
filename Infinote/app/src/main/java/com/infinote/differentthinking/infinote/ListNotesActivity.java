package com.infinote.differentthinking.infinote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);

        Button newNoteButton = (Button) this.findViewById(R.id.newNoteButton);
        Button accountButton = (Button) this.findViewById(R.id.accountButton);

        newNoteButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(ListNotesActivity.this, NoteActivity.class);
                        startActivity(intent);
                    }
                }
        );

        accountButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(ListNotesActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }
}
