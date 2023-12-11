package com.example.mymap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        TextView textView = findViewById(R.id.textView);
        Intent intent = getIntent();


        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("username", "");
        textView.setText("Welcome " + s + " to notes app !");

    }

    public void logout(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    }


