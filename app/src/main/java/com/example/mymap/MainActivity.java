package com.example.mymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public void clickFunction(View view) {
        EditText userIdEditText = findViewById(R.id.textUserName);
        String userId = userIdEditText.getText().toString();

        userId = sanitizeUserId(userId);
        // Check if the user ID is not empty
        if (!userId.isEmpty()) {
            // Save user ID in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
            sharedPreferences.edit().putString("username", userId).apply();

            // Launch HomePage activity
            goToActivity();
        }
    }
    private String sanitizeUserId(String userId) {
        // Define your regex pattern for a valid user ID
        String regex = "[a-zA-Z0-9]+"; // Example: Alphanumeric characters only

        // Remove any characters that do not match the pattern
        return userId.replaceAll("[^a-zA-Z0-9]", "");
    }
    public void goToActivity() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);

        if (!sharedPreferences.getString("username", "").equals("")) {
            goToActivity();
        } else {
            setContentView(R.layout.activity_main);
        }
    }



}