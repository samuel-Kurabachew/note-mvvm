package com.sam.architectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_DESCRIPTION = "DESCRIPTION";
    public static final String EXTRA_PRIORITY = "PRIORITY";

    private EditText titleInput;
    private EditText descriptionInput;
    private NumberPicker priorityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleInput = findViewById(R.id.title_input);
        descriptionInput = findViewById(R.id.description_input);
        priorityPicker = findViewById(R.id.priority_number_picker);

        priorityPicker.setMinValue(1);
        priorityPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");

            titleInput.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionInput.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priorityPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }


    }

    private void saveNote() {
        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        int priority = priorityPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please enter the title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent(this, MainActivity.class);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
