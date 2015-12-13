package com.haplicity.lifecounter;

import android.content.Context;
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

public class SignUpActivity extends AppCompatActivity {

    Context context;

    EditText mUsername, mPassword, mConfirmPassword;
    Button mSignUp;

    String username;
    String password;

    DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = getApplicationContext();

        dataStore = DataStore.get(context);

        mUsername = (EditText)findViewById(R.id.username_editText);
        mPassword = (EditText)findViewById(R.id.password_editText);
        mConfirmPassword = (EditText)findViewById(R.id.confirmPassword_editText);

        //setup SignUp onClick listener
        mSignUp = (Button)findViewById(R.id.signUp_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsername.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast toast = Toast.makeText(context, "All fields required!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (!password.equals(confirmPassword)) {
                    Toast toast = Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT);
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

                    //send signup event to server
                    dataStore.mSocket.emit("signup", array);
                }
            }
        });

        //upon receiving events, run the appropriate function
        dataStore.mSocket.on("signupResult", onSignup);
    }

    //runs when signupResult event received from server
    public Emitter.Listener onSignup = new Emitter.Listener() {
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
                //set the username and password
                dataStore.setUsername(username);
                dataStore.setPassword(password);
                dataStore.setId(id);
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
