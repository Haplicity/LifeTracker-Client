package com.haplicity.lifecounter;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Haplicity on 12/6/2015.
 */
public class DataStore {
    private static DataStore sDataStore;
    private String username;
    private String password;
    private String id;

    private RoomInfo selectedRoom;

    public static final String SHARED_PREF = "SHARED_PREF";
    public static final String USERNAME_KEY = "USERNAME";
    public static final String PASSWORD_KEY = "PASSWORD";
    public static final String ID_KEY = "ID";

    //socket used to communicate with server
    public Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://quiet-peak-8067.herokuapp.com/");
        } catch (URISyntaxException e) {}
    }

    //constructor
    public static DataStore get(Context context) {
        if(sDataStore == null) {
            sDataStore = new DataStore(context);
        }
        return sDataStore;
    }

    //gets user credentials from SharedPreferences
    private DataStore(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        username = settings.getString(USERNAME_KEY, "");
        password = settings.getString(PASSWORD_KEY, "");
        id = settings.getString(ID_KEY, "");

        mSocket.connect();
    }

    //saves user credentials
    public boolean saveCredentials(Context context){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(ID_KEY, id);

        return editor.commit();
    }

    //Getters and setters for various variables
    public String getUsername() {
        return username;
    }

    public void setUsername(String s) {
        username = s;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String s) {
        password = s;
    }

    public String getId() {
        return id;
    }

    public void setId(String s) {
        id = s;
    }

    public RoomInfo getRoom() {
        return selectedRoom;
    }

    public void setRoom(RoomInfo r) {
        selectedRoom = r;
    }
}
