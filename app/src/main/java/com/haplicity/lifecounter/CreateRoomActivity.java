package com.haplicity.lifecounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateRoomActivity extends AppCompatActivity {

    EditText mTitle, mDescription;
    Button mCreateRoom, mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mTitle = (EditText)findViewById(R.id.title_editText);
        mDescription = (EditText)findViewById(R.id.description_editText);

        mCreateRoom = (Button)findViewById(R.id.createRoom_button);
        mCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();

                Intent intent = new Intent(CreateRoomActivity.this, SearchActivity.class);
                //Pass data back to parent activity
                intent.putExtra(SearchActivity.TITLE_DATA, title);
                intent.putExtra(SearchActivity.DESCRIPTION_DATA, description);
                setResult(SearchActivity.RESULT_OK, intent);
                finish();
            }
        });

        mCancel = (Button)findViewById(R.id.cancel_button);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
