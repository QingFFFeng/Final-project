package com.cs407.lab5_milestone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ResourceManagerInternal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotePage extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private String username;
    public static ArrayList<Notes> notes1 = new ArrayList<>();
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepage);

        //1. Display welcome message. Get the username from SharedPreferences
        textView = (TextView) findViewById(R.id.welcome);
        Intent intent = getIntent();
        username = intent.getStringExtra("message");
        textView.setText("Welcome " + username + " to notes app!");
        //2. Get SOLiteDatabase Instance
        sqLiteDatabase = openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        //3. Initialize the "notes" class variable using readNotes method implemented in the DBHelper class.
        dbHelper = new DBHelper(sqLiteDatabase);
        dbHelper.createTable();
        //  use the username you got from SharedPreferences as a parameter to readNotes method.
        //4 .Create an ArrayList <String> object for iterating over notes.
        ArrayList<String> displayNotes = new ArrayList<>();
        ArrayList<Notes> notesList = dbHelper.readNotes(username);
        for (Notes note : notesList) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView notesListView = (ListView) findViewById(R.id.list);
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), noteWriting.class);
                intent.putExtra("noteId", i);
//                startActivity(intent);
                openNoteWritingActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_note) {
            openNoteWritingActivity(-1);

        } else if (id == R.id.action_logout) {
            clearSharedPreferences();
            goToMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username"); // Remove the username
        editor.apply();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openNoteWritingActivity(int noteId) {
        Intent intent = new Intent(this, noteWriting.class);
        intent.putExtra("noteId", noteId);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
