package com.haplicity.lifecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.emitter.Emitter;

public class SearchActivity extends AppCompatActivity {

    Context context;

    //keys used for transferring data to other activities
    static final int CREATOR_ACTIVITY_REQUEST = 1;
    public final static String TITLE_DATA = "TITLE";
    public final static String CREATOR_DATA = "CREATOR";
    public final static String PLAYERS = "PLAYERS";
    public final static String PLAYER1_DATA = "PLAYER1";
    public final static String PLAYER2_DATA = "PLAYER2";
    public final static String PLAYER3_DATA = "PLAYER3";
    public final static String PLAYER4_DATA = "PLAYER4";
    public final static String PLAYER1LIFE_DATA = "PLAYER1LIFE";
    public final static String PLAYER2LIFE_DATA = "PLAYER2LIFE";
    public final static String PLAYER3LIFE_DATA = "PLAYER3LIFE";
    public final static String PLAYER4LIFE_DATA = "PLAYER4LIFE";

    Button mJoinRoom, mCreateRoom, mRefreshList;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<RoomInfo> rooms;

    DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = getApplicationContext();

        //Setup JoinRoom's onClick listener
        mJoinRoom = (Button)findViewById(R.id.joinRoom_button);
        mJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataStore.getRoom() != null) {
                    String roomName = dataStore.getRoom().title.trim();
                    String roomCreator = dataStore.getRoom().creator.trim();

                    JSONObject obj = new JSONObject();
                    JSONArray array = new JSONArray();
                    try {
                        obj.put("roomName", roomName);
                        obj.put("creator", roomCreator);
                        obj.put("username", dataStore.getUsername());
                    } catch (JSONException e) { }

                    array.put(obj);

                    //send joinRoom event to server
                    dataStore.mSocket.emit("joinRoom", array);
                } else {
                    Toast toast = Toast.makeText(context, "No room selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //Setup RefreshList's onClick listener
        mRefreshList = (Button)findViewById(R.id.refreshList_button);
        mRefreshList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send getRoom event to server
                dataStore.mSocket.emit("getRooms");
            }
        });

        //Setup CreateRoom's onClick listener
        mCreateRoom = (Button)findViewById(R.id.createRoom_button);
        mCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the Create Room activity
                Intent intent = new Intent(SearchActivity.this, CreateRoomActivity.class);
                startActivityForResult(intent, CREATOR_ACTIVITY_REQUEST);
            }
        });

        rooms = new ArrayList<>();

        //initialize RecyclerView
        mRecyclerView = (RecyclerView)findViewById(R.id.roomList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set the adapter
        mAdapter = new RoomAdapter(rooms, context);
        mRecyclerView.setAdapter(mAdapter);

        dataStore = DataStore.get(context);

        //if no user is logged in, launch Login activity
        if (dataStore.getUsername().isEmpty() || dataStore.getPassword().isEmpty()) {
            Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        //send getRoom event to server
        dataStore.mSocket.emit("getRooms");

        //upon receiving events, run the appropriate function
        dataStore.mSocket.on("getRoomResult", onGetRoom);
        dataStore.mSocket.on("joinRoomResult", onJoinRoom);
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

                //launch Room activity, passing in data
                Intent intent = new Intent(SearchActivity.this, RoomActivity.class);
                intent.putExtra(TITLE_DATA, titleString);
                intent.putExtra(CREATOR_DATA, dataStore.getId());
                intent.putExtra(PLAYER1_DATA, dataStore.getUsername());
                intent.putExtra(PLAYER1LIFE_DATA, 40);
                intent.putExtra(PLAYERS, 1);
                startActivity(intent);
            }
        }
    }

    //clear the RecyclerView
    public void clearData() {
        int size = rooms.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                rooms.remove(0);
            }

            mAdapter.notifyItemRangeRemoved(0, size);
        }
    }

    //runs when getRoomResults event received from server
    public Emitter.Listener onGetRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            JSONArray roomArray = new JSONArray();
            try {
                //get values from data
                success = data.getBoolean("success");
                if (success) {
                    roomArray = data.getJSONArray("rooms");
                }
            } catch (JSONException e) { return; }

            if (success) {
                //clear the RecyclerView
                clearData();
                dataStore.setRoom(null);

                //populate RecyclerView with rooms
                for (int i = 0; i < roomArray.length(); i++) {
                    RoomInfo tempInfo = new RoomInfo();
                    JSONObject tempRooms;

                    try {
                        tempRooms = roomArray.getJSONObject(i);
                        JSONArray tempUsers = tempRooms.getJSONArray("users");

                        tempInfo.title = tempRooms.getString("name");
                        tempInfo.description = tempRooms.getString("description");
                        tempInfo.creator = tempRooms.getString("creator");
                        tempInfo.currentPlayers = tempUsers.length();

                        if (tempUsers.length() > 0) {
                            tempInfo.player1 = tempUsers.get(0).toString();
                        }
                        if (tempUsers.length() > 1) {
                            tempInfo.player2 = tempUsers.get(1).toString();
                        }
                        if (tempUsers.length() > 2) {
                            tempInfo.player3 = tempUsers.get(2).toString();
                        }
                        if (tempUsers.length() > 3) {
                            tempInfo.player4 = tempUsers.get(3).toString();
                        }

                        tempInfo.maxPlayers = 4;

                    } catch (JSONException e) { return; }

                    rooms.add(tempInfo);
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
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

    //runs when joinRoomResults event received from server
    public Emitter.Listener onJoinRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            JSONObject room;
            JSONArray life;
            JSONArray users = new JSONArray();

            String roomName = "";
            String roomCreator = "";

            String player1 = "";
            String player2 = "";
            String player3 = "";
            String player4 = "";

            int player1Life = 40;
            int player2Life = 40;
            int player3Life = 40;
            int player4Life = 40;

            try {
                //get values from data
                success = data.getBoolean("success");
                if (success) {
                    room = data.getJSONObject("room");
                    life = data.getJSONArray("life");

                    roomName = room.getString("name");
                    roomCreator = room.getString("creator");
                    users = room.getJSONArray("users");

                    if (users.length() > 0) {
                        player1 = users.getString(0);
                        player1Life = life.getInt(0);
                    }
                    if (users.length() > 1) {
                        player2 = users.getString(1);
                        player2Life = life.getInt(1);
                    }
                    if (users.length() > 2) {
                        player3 = users.getString(2);
                        player3Life = life.getInt(2);
                    }
                    if (users.length() > 3) {
                        player4 = users.getString(3);
                        player4Life = life.getInt(3);
                    }
                }
            } catch (JSONException e) { return; }

            //pass data to Room activity and launch Room activity
            if (success) {
                Intent intent = new Intent(SearchActivity.this, RoomActivity.class);
                intent.putExtra(TITLE_DATA, roomName);
                intent.putExtra(CREATOR_DATA, roomCreator);
                intent.putExtra(PLAYERS, users.length());

                if (users.length() > 0) {
                    intent.putExtra(PLAYER1_DATA, player1);
                    intent.putExtra(PLAYER1LIFE_DATA, player1Life);
                }
                if (users.length() > 1) {
                    intent.putExtra(PLAYER2_DATA, player2);
                    intent.putExtra(PLAYER2LIFE_DATA, player2Life);
                }
                if (users.length() > 2) {
                    intent.putExtra(PLAYER3_DATA, player3);
                    intent.putExtra(PLAYER3LIFE_DATA, player3Life);
                }
                if (users.length() > 3) {
                    intent.putExtra(PLAYER4_DATA, player4);
                    intent.putExtra(PLAYER4LIFE_DATA, player4Life);
                }

                startActivity(intent);
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
