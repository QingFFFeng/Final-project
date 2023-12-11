package com.example.mymap;
import com.example.mymap.NotePage;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

    public void goToNotePage(View view) {
        Intent intent = new Intent(this, NotePage.class);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        intent.putExtra("message", username);
        startActivity(intent);
    }

    public void goToGoogleMaps(View view) {
        // Create an intent with a geo URI to open Google Maps
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Googleplex,Google+Inc.,Mountain+View,CA");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // Check if there is an app to handle the intent
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
        }
    }

}


