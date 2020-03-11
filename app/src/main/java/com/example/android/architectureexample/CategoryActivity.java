package com.example.android.architectureexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.android.architectureexample.AddEditCategoryActivity.EXTRA_ID;
import static com.example.android.architectureexample.AddEditCategoryActivity.EXTRA_NAME;

public class CategoryActivity extends AppCompatActivity {

    private static final int ADD_CATEGORY_REQUEST = 1;
    public static final int EDIT_CATEGORY_REQUEST = 2;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        FloatingActionButton buttonAddCategory = findViewById(R.id.button_add_category);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, AddEditCategoryActivity.class);
                startActivityForResult(intent, ADD_CATEGORY_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.submitList(categories);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                categoryViewModel.delete(adapter.getCategoryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CategoryActivity.this, "Category deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent intent = new Intent(CategoryActivity.this, AddEditCategoryActivity.class);
                intent.putExtra(EXTRA_ID, category.getId());
                intent.putExtra(EXTRA_NAME, category.getName());
                startActivityForResult(intent, EDIT_CATEGORY_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditCategoryActivity.EXTRA_NAME);

            Category category = new Category(name);
            categoryViewModel.insert(category);

            Toast.makeText(this, "Category saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_CATEGORY_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Category can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(EXTRA_NAME);

            Category category = new Category(name);
            category.setId(id);
            categoryViewModel.update(category);

            Toast.makeText(this, "Category updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Category not saves", Toast.LENGTH_SHORT).show();
        }

    }
}
