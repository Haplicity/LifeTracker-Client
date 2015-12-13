package com.haplicity.lifecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class CreateRoomActivity extends AppCompatActivity {

    Context context;

    EditText mTitle, mDescription;
    Button mCreateRoom, mCancel;

    String title;
    String description;

    DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        context = getApplicationContext();

        dataStore = DataStore.get(context);

        mTitle = (EditText)findViewById(R.id.title_editText);
        mDescription = (EditText)findViewById(R.id.description_editText);

        //setup CreateRoom onClick listener
        mCreateRoom = (Button)findViewById(R.id.createRoom_button);
        mCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = mTitle.getText().toString();
                description = mDescription.getText().toString();

                if (title.isEmpty() || description.isEmpty()) {
                    Toast toast = Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    JSONObject obj = new JSONObject();
                    JSONArray array = new JSONArray();
                    try {
                        obj.put("roomName", title);
                        obj.put("description", description);
                        obj.put("creator", dataStore.getId());
                        obj.put("username", dataStore.getUsername());
                    } catch (JSONException e) {
                    }

                    array.put(obj);

                    //sends createRoom event to server
                    dataStore.mSocket.emit("createRoom", array);
                }
            }
        });

        //setup Cancel button onClick listener
        mCancel = (Button)findViewById(R.id.cancel_button);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ends this activity
                finish();
            }
        });

        //upon receiving events, run the appropriate function
        dataStore.mSocket.on("createRoomResult", onCreateRoom);
    }
    //runs when createRoomResults event received from server
    public Emitter.Listener onCreateRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            try {
                success = data.getBoolean("success");
            } catch (JSONException e) { return; }

            if (success) {
                //return back to parent activity, passing data
                Intent intent = new Intent(CreateRoomActivity.this, SearchActivity.class);
                //Pass data back to parent activity
                intent.putExtra(SearchActivity.TITLE_DATA, title);
                setResult(SearchActivity.RESULT_OK, intent);
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast = Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }
    };
}
