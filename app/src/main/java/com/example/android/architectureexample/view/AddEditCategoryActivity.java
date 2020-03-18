package com.example.android.architectureexample.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.architectureexample.R;

public class AddEditCategoryActivity extends AppCompatActivity {
    public static final String EXTRA_NAME =
            "com.example.android.architectureexample.category.EXTRA_NAME";
    public static final String EXTRA_ID =
            "com.example.android.architectureexample.category.EXTRA_ID";

    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        editTextName = findViewById(R.id.edit_text_name);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit category");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
        } else {
            setTitle("Add category");
        }
    }

    private void saveCategory() {
        String name = editTextName.getText().toString();

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);

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
        menuInflater.inflate(R.menu.add_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_category:
                saveCategory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
