package com.haplicity.lifecounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RoomActivity extends AppCompatActivity {

    TextView mPlayer1Name, mPlayer2Name, mPlayer3Name, mPlayer4Name;
    TextView mPlayer1Life, mPlayer2Life, mPlayer3Life, mPlayer4Life;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mPlayer1Name = (TextView)findViewById(R.id.player1_name);
        mPlayer1Life = (TextView)findViewById(R.id.player1_life);

        mPlayer2Name = (TextView)findViewById(R.id.player2_name);
        mPlayer2Life = (TextView)findViewById(R.id.player2_life);

        mPlayer3Name = (TextView)findViewById(R.id.player3_name);
        mPlayer3Life = (TextView)findViewById(R.id.player3_life);

        mPlayer4Name = (TextView)findViewById(R.id.player4_name);
        mPlayer4Life = (TextView)findViewById(R.id.player4_life);
    }
}
