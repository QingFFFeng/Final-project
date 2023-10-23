package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void loginButtonClicked(View view) {
        Log.i("INFO", "Button Pressed");
        EditText myTextField = (EditText) findViewById(R.id.username);
        String username = myTextField.getText().toString();
        goToActivity(myTextField.getText().toString());

        // Save the username in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();

        // Go to the NotePage activity
        goToActivity(username);
    }

    public void goToActivity(String s){
        Intent intent = new Intent(this, NotePage.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");

        if (!savedUsername.isEmpty()) {
            // User didn't log out, go directly to NotePage
            goToActivity(savedUsername);
            return;
        }

        setContentView(R.layout.activity_main);
    }
}
