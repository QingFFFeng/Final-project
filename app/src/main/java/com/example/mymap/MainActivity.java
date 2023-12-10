package com.example.mymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view) {
        EditText userid = (EditText) findViewById(R.id.textUserName);
        goToActivity();
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        sharedPreferences.edit().putString("username", userid.getText().toString()).apply();
    }

    public void goToActivity() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
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