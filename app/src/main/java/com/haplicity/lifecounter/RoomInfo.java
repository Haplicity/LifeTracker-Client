package com.haplicity.lifecounter;

/**
 * Created by Haplicity on 11/28/2015.
 */
public class RoomInfo {
    public int index;
    protected String title;
    protected String creator;
    protected String description;
    protected int currentPlayers;
    protected int maxPlayers;

    public RoomInfo() {
        index = -1;
        title = "TITLE";
        creator = "CREATOR";
        description = "This is a room description";
        currentPlayers = 1;
        maxPlayers = 4;
    }
}
