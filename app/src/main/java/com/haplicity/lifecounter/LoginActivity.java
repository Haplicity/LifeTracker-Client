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

    DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();

        dataStore = DataStore.get(context);

        mUsername = (EditText)findViewById(R.id.username_editText);
        mPassword = (EditText)findViewById(R.id.password_editText);

        mLogin = (Button)findViewById(R.id.login_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast toast = Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    JSONObject obj = new JSONObject();
                    JSONArray array = new JSONArray();
                    try {
                        obj.put("username", username);
                        obj.put("pass", password);
                    } catch (JSONException e) { }

                    array.put(obj);
                    dataStore.mSocket.emit("login", array);
                }
            }
        });

        mSignUp = (Button)findViewById(R.id.signUp_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        dataStore.mSocket.on("loginResult", onLogin);

        if (dataStore.getUsername().isEmpty() && dataStore.getPassword().isEmpty()) {
            finish();
        }
    }

    public Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean success;
            try {
                success = data.getBoolean("success");

            } catch (JSONException e) {
                return;
            }

            if (success) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                dataStore.setUsername(username);
                dataStore.setPassword(password);
                finish();
            } else {
                Toast toast = Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };
}
