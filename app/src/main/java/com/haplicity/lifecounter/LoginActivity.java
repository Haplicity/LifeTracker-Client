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

public class LoginActivity extends AppCompatActivity {

    Context context;

    EditText mUsername, mPassword;
    Button mLogin, mSignUp;

    String username;
    String password;

    DataStore dataStore;

    boolean badLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();

        dataStore = DataStore.get(context);

        mUsername = (EditText)findViewById(R.id.username_editText);
        mPassword = (EditText)findViewById(R.id.password_editText);

        //setup login onClick listener
        mLogin = (Button)findViewById(R.id.login_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsername.getText().toString().trim();
                password = mPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT).show();
                }
                else {
                    JSONObject obj = new JSONObject();
                    JSONArray array = new JSONArray();
                    try {
                        obj.put("username", username);
                        obj.put("pass", password);
                    } catch (JSONException e) { }

                    array.put(obj);

                    //send login event to server
                    dataStore.mSocket.emit("login", array);
                }
            }
        });

        //setup signUp onClick listener
        mSignUp = (Button)findViewById(R.id.signUp_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start SignUp activity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //upon receiving events from server, launch appropriate function
        dataStore.mSocket.on("loginResult", onLogin);

        if (!dataStore.getUsername().isEmpty() && !dataStore.getPassword().isEmpty()) {
            finish();
        }
    }

    //runs when receiving loginResult event from server
    public Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            String id = "";
            try {
                //retrieve data
                success = data.getBoolean("success");
                if (success) {
                    id = data.getString("id");
                }
            } catch (JSONException e) { return; }

            if (success) {
                //store and save user credentials, and exit activity
                dataStore.setUsername(username);
                dataStore.setPassword(password);
                dataStore.setId(id);
                dataStore.saveCredentials(context);
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast = Toast.makeText(context, "Username/password invalid", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }
    };
}
