package com.haplicity.lifecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class RoomActivity extends AppCompatActivity {

    Context context;

    TextView mPlayer1Name, mPlayer2Name, mPlayer3Name, mPlayer4Name;
    TextView mPlayer1Life, mPlayer2Life, mPlayer3Life, mPlayer4Life;

    Button mPlayer1PlusOne, mPlayer2PlusOne, mPlayer3PlusOne, mPlayer4PlusOne;
    Button mPlayer1PlusFive, mPlayer2PlusFive, mPlayer3PlusFive, mPlayer4PlusFive;
    Button mPlayer1MinusOne, mPlayer2MinusOne, mPlayer3MinusOne, mPlayer4MinusOne;
    Button mPlayer1MinusFive, mPlayer2MinusFive, mPlayer3MinusFive, mPlayer4MinusFive;

    String roomName, creator;
    int users;
    String player1, player2, player3, player4;
    int player1Life, player2Life,player3Life, player4Life;

    DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        context = getApplicationContext();

        dataStore = DataStore.get(context);

        mPlayer1Name = (TextView)findViewById(R.id.player1_name);
        mPlayer1Life = (TextView)findViewById(R.id.player1_life);

        mPlayer2Name = (TextView)findViewById(R.id.player2_name);
        mPlayer2Life = (TextView)findViewById(R.id.player2_life);

        mPlayer3Name = (TextView)findViewById(R.id.player3_name);
        mPlayer3Life = (TextView)findViewById(R.id.player3_life);

        mPlayer4Name = (TextView)findViewById(R.id.player4_name);
        mPlayer4Life = (TextView)findViewById(R.id.player4_life);

        //setup onClick listeners; there's a lot of them
        mPlayer1PlusOne = (Button)findViewById(R.id.player1PlusOne_button);
        mPlayer1PlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", 1);
                } catch (JSONException e) { }

                array.put(obj);

                //send updateLife event to server
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer1PlusFive = (Button)findViewById(R.id.player1PlusFive_button);
        mPlayer1PlusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", 5);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer1MinusOne = (Button)findViewById(R.id.player1MinusOne_button);
        mPlayer1MinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", -1);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer1MinusFive = (Button) findViewById(R.id.player1MinusFive_button);
        mPlayer1MinusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", -5);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });

        mPlayer2PlusOne = (Button)findViewById(R.id.player2PlusOne_button);
        mPlayer2PlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", 1);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer2PlusFive = (Button)findViewById(R.id.player2PlusFive_button);
        mPlayer2PlusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", 5);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer2MinusOne = (Button)findViewById(R.id.player2MinusOne_button);
        mPlayer2MinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", -1);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer2MinusFive = (Button) findViewById(R.id.player2MinusFive_button);
        mPlayer2MinusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", -5);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });

        mPlayer3PlusOne = (Button)findViewById(R.id.player3PlusOne_button);
        mPlayer3PlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", 1);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer3PlusFive = (Button)findViewById(R.id.player3PlusFive_button);
        mPlayer3PlusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", 5);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer3MinusOne = (Button)findViewById(R.id.player3MinusOne_button);
        mPlayer3MinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", -1);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer3MinusFive = (Button) findViewById(R.id.player3MinusFive_button);
        mPlayer3MinusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", -5);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });

        mPlayer4PlusOne = (Button)findViewById(R.id.player4PlusOne_button);
        mPlayer4PlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", 1);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer4PlusFive = (Button)findViewById(R.id.player4PlusFive_button);
        mPlayer4PlusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", 5);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer4MinusOne = (Button)findViewById(R.id.player4MinusOne_button);
        mPlayer4MinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", -1);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });
        mPlayer4MinusFive = (Button) findViewById(R.id.player4MinusFive_button);
        mPlayer4MinusFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();
                try {
                    obj.put("roomName", roomName);
                    obj.put("creator", creator);
                    obj.put("username", dataStore.getUsername());
                    obj.put("gainNumber", -5);
                } catch (JSONException e) { }

                array.put(obj);
                dataStore.mSocket.emit("updateLife", array);
            }
        });

        //retrieve data passed to this activity
        Intent intent = getIntent();
        roomName = intent.getStringExtra(SearchActivity.TITLE_DATA).trim();
        creator = intent.getStringExtra(SearchActivity.CREATOR_DATA).trim();
        users = intent.getIntExtra(SearchActivity.PLAYERS, 0);

        //populate text fields with appropriate data
        if (users > 0) {
            player1 = intent.getStringExtra(SearchActivity.PLAYER1_DATA).trim();
            player1Life = intent.getIntExtra(SearchActivity.PLAYER1LIFE_DATA, 0);

            mPlayer1Name.setText(player1);
            mPlayer1Life.setText("" + player1Life);

            if (player1.equals(dataStore.getUsername())) {
                mPlayer1PlusOne.setVisibility(View.VISIBLE);
                mPlayer1PlusFive.setVisibility(View.VISIBLE);
                mPlayer1MinusOne.setVisibility(View.VISIBLE);
                mPlayer1MinusFive.setVisibility(View.VISIBLE);
            }
        } else {
            player1 = "";
            player1Life = 0;
        }
        if (users > 1) {
            player2 = intent.getStringExtra(SearchActivity.PLAYER2_DATA).trim();
            player2Life = intent.getIntExtra(SearchActivity.PLAYER2LIFE_DATA, 0);

            mPlayer2Name.setText(player2);
            mPlayer2Life.setText(""+player2Life);

            if (player2.equals(dataStore.getUsername())) {
                mPlayer2PlusOne.setVisibility(View.VISIBLE);
                mPlayer2PlusFive.setVisibility(View.VISIBLE);
                mPlayer2MinusOne.setVisibility(View.VISIBLE);
                mPlayer2MinusFive.setVisibility(View.VISIBLE);
            }
        } else {
            player2 = "";
            player2Life = 0;
        }
        if (users > 2) {
            player3 = intent.getStringExtra(SearchActivity.PLAYER3_DATA).trim();
            player3Life = intent.getIntExtra(SearchActivity.PLAYER3LIFE_DATA, 0);

            mPlayer3Name.setText(player3);
            mPlayer3Life.setText(""+player3Life);

            if (player3.equals(dataStore.getUsername())) {
                mPlayer3PlusOne.setVisibility(View.VISIBLE);
                mPlayer3PlusFive.setVisibility(View.VISIBLE);
                mPlayer3MinusOne.setVisibility(View.VISIBLE);
                mPlayer3MinusFive.setVisibility(View.VISIBLE);
            }
        } else {
            player3 = "";
            player3Life = 0;
        }
        if (users > 3) {
            player4 = intent.getStringExtra(SearchActivity.PLAYER4_DATA).trim();
            player4Life = intent.getIntExtra(SearchActivity.PLAYER4LIFE_DATA, 0);

            mPlayer4Name.setText(player4);
            mPlayer4Life.setText(""+player4Life);

            if (player4.equals(dataStore.getUsername())) {
                mPlayer4PlusOne.setVisibility(View.VISIBLE);
                mPlayer4PlusFive.setVisibility(View.VISIBLE);
                mPlayer4MinusOne.setVisibility(View.VISIBLE);
                mPlayer4MinusFive.setVisibility(View.VISIBLE);
            }
        } else {
            player4 = "";
            player4Life = 0;
        }

        //upon receiving events from server, run appropriate function
        dataStore.mSocket.on("updateLifeResult", onUpdateLife);
        dataStore.mSocket.on("userJoinedRoom", onUserJoinedRoom);
        dataStore.mSocket.on("userLeftRoom", onUserLeftRoom);
        dataStore.mSocket.on("leaveRoomResult", onLeaveRoom);
    }

    //runs when back button is pressed
    @Override
    public void onBackPressed() {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            obj.put("roomName", roomName);
            obj.put("creator", creator);
            obj.put("username", dataStore.getUsername());
        } catch (JSONException e) {
        }

        array.put(obj);

        //send leaveRoom event to server
        dataStore.mSocket.emit("leaveRoom", array);
    }

    //runs after receiving leaveRoomResults event from server
    public Emitter.Listener onLeaveRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            try {
                success = data.getBoolean("success");
            } catch (JSONException e) { return; }

            if (success) {
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

    //runs after receiving updateLife event from server
    public Emitter.Listener onUpdateLife = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            String username = "";
            int life = 0;
            try {
                //retrieve data
                success = data.getBoolean("success");
                if (success) {
                    username = data.getString("username");
                    life = data.getInt("lifeGain");
                }
            } catch (JSONException e) { return; }

            if (success) {
                //update text fields with new values
                if (player1.equals(username)) {
                    player1Life += life;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer1Life.setText("" + player1Life);
                        }
                    });
                } else if (player2.equals(username)) {
                    player2Life += life;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer2Life.setText("" + player2Life);
                        }
                    });
                } else if (player3.equals(username)) {
                    player3Life += life;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer3Life.setText("" + player3Life);
                        }
                    });
                } else if (player4.equals(username)) {
                    player4Life += life;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer4Life.setText("" + player4Life);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast toast = Toast.makeText(context, "Error: Player could not be found", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
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

    //runs when receiving userJoinedRoom event from server
    public Emitter.Listener onUserJoinedRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            String username = "";
            try {
                //retrieve data
                success = data.getBoolean("success");
                if (success) {
                    username = data.getString("username");
                }
            } catch (JSONException e) { return; }

            if (success) {
                //populates text fields with new user data
                if (player1.isEmpty()) {
                    player1 = username;
                    player1Life = 40;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer1Name.setText(player1);
                            mPlayer1Life.setText("" + player1Life);
                        }
                    });
                } else if (player2.isEmpty()) {
                    player2 = username;
                    player2Life = 40;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer2Name.setText(player2);
                            mPlayer2Life.setText("" + player2Life);
                        }
                    });
                } else if (player3.isEmpty()) {
                    player3 = username;
                    player3Life = 40;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer3Name.setText(player3);
                            mPlayer3Life.setText("" + player3Life);
                        }
                    });
                } else if (player4.isEmpty()) {
                    player4 = username;
                    player4Life = 40;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer4Name.setText(player2);
                            mPlayer4Life.setText("" + player4Life);
                        }
                    });
                }
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

    //runs after receiving userLeftRoom event from server
    public Emitter.Listener onUserLeftRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            String username = "";
            try {
                //retrieve data
                success = data.getBoolean("success");
                if (success) {
                    username = data.getString("username");
                }
            } catch (JSONException e) { return; }

            if (success) {
                //removes user data from text fields, replacing with default data
                if (player1.equals(username)) {
                    player1 = "";
                    player1Life = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer1Name.setText("Player 1");
                            mPlayer1Life.setText("40");
                        }
                    });
                } else if (player2.equals(username)) {
                    player2 = "";
                    player2Life = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer2Name.setText("Player 2");
                            mPlayer2Life.setText("40");
                        }
                    });
                } else if (player3.equals(username)) {
                    player3 = "";
                    player3Life = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer3Name.setText("Player 3");
                            mPlayer3Life.setText("40");
                        }
                    });
                } else if (player4.equals(username)) {
                    player4 = "";
                    player4Life = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mPlayer2Name.setText("Player 4");
                            mPlayer2Life.setText("40");
                        }
                    });
                }
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
