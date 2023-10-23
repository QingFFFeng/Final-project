package com.cs407.lab5_milestone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class noteWriting extends AppCompatActivity {

    private EditText editText;
    private Button buttonSave;
    private Button buttonDelete;
    private int noteId = -1;
    private String username;
    private String title;
    private String content;
    private String date;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notewriting);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        editText = findViewById(R.id.writehere);
        buttonSave = findViewById(R.id.save);
        buttonDelete = findViewById(R.id.delete);

        if (noteId != -1) {
            loadExistingNote();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrUpdateNote();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });
    }

    private void loadExistingNote() {
        context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        ArrayList<Notes> notesList = dbHelper.readNotes(username);
        content = notesList.get(noteId).getContent();
        editText.setText(content);
    }
    private void saveOrUpdateNote() {
        content = editText.getText().toString();
        context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        date = dateFormat.format(new Date());

        if (noteId == -1) {
            context = getApplicationContext();
            sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
            title = "NOTES_" + (dbHelper.readNotes(username).size() + 1);
            dbHelper = new DBHelper(sqLiteDatabase);
            dbHelper.saveNotes(username, title, date, content);
        } else {
            title = "NOTES_" + (noteId + 1);
            dbHelper.updateNotes(content, date, title, username);
        }
        Intent intent = new Intent();
        intent.putExtra("refresh", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void deleteNote() {
        context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        editText = findViewById(R.id.writehere);
        content = editText.getText().toString();
        title = "NOTES_" + (noteId + 1);
        dbHelper.deleteNotes(content, title);
        Intent intent = new Intent();
        intent.putExtra("refresh", true);
        setResult(RESULT_OK, intent);
        finish();
    }

}
