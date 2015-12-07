package com.haplicity.lifecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Context context;

    static final int CREATOR_ACTIVITY_REQUEST = 1;
    public final static String TITLE_DATA = "TITLE";
    public final static String DESCRIPTION_DATA = "DESCRIPTION";

    Button mJoinRoom, mCreateRoom;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<RoomInfo> rooms;

    DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = getApplicationContext();

        mJoinRoom = (Button)findViewById(R.id.joinRoom_button);
        mJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCreateRoom = (Button)findViewById(R.id.createRoom_button);
        mCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, CreateRoomActivity.class);
                startActivityForResult(intent, CREATOR_ACTIVITY_REQUEST);
            }
        });

        rooms = new ArrayList<>();

        mRecyclerView = (RecyclerView)findViewById(R.id.roomList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new RoomAdapter(rooms);
        mRecyclerView.setAdapter(mAdapter);

        dataStore = DataStore.get(context);

        if (dataStore.getUsername().isEmpty() || dataStore.getPassword().isEmpty()) {
            Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CREATOR_ACTIVITY_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Retrieve the data
                String titleString = data.getStringExtra(TITLE_DATA);
                String descriptionString = data.getStringExtra(DESCRIPTION_DATA);

                RoomInfo tempRoom = new RoomInfo();
                tempRoom.title = titleString;
                tempRoom.creator = "John Smith";
                tempRoom.description = descriptionString;
                tempRoom.currentPlayers = 1;
                tempRoom.maxPlayers = 4;
                rooms.add(tempRoom);

                Intent intent = new Intent(SearchActivity.this, RoomActivity.class);
                //startActivity(intent);
            }
        }
    }
}
