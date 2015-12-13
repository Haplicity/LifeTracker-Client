package com.haplicity.lifecounter;

/**
 * Created by Haplicity on 11/28/2015.
 */
public class RoomInfo {
    protected String title;
    protected String description;
    protected String creator;
    protected int currentPlayers;
    protected int maxPlayers;
    protected String player1;
    protected String player2;
    protected String player3;
    protected String player4;

    public RoomInfo() {
        title = "TITLE";
        description = "This is a room description";
        creator = "CREATOR";
        currentPlayers = 1;
        maxPlayers = 4;
        player1 = "Player1";
        player2 = "Player2";
        player3 = "Player3";
        player4 = "Player4";
    }
}
