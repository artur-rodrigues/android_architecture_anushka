package com.androidtutz.anushka.ebookshop.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidtutz.anushka.ebookshop.R;
import com.androidtutz.anushka.ebookshop.databinding.ActivityMainBinding;
import com.androidtutz.anushka.ebookshop.model.entities.Book;
import com.androidtutz.anushka.ebookshop.model.entities.Category;
import com.androidtutz.anushka.ebookshop.view.adapter.BooksAdapter;
import com.androidtutz.anushka.ebookshop.viewmodel.MainActivityViewModel;

import java.util.List;

import static com.androidtutz.anushka.ebookshop.view.activity.AddEditActivity.BOOK;

public class MainActivity extends AppCompatActivity {

    private final int ADD_REQUEST = 1;
    private final int EDIT_REQUEST = 2;

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private List<Category> categories;
    private List<Book> books;
    private BooksAdapter adapter = new BooksAdapter();

    private Category selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        setUpRecyclerView();

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.fetchCategories();
        viewModel.getCategories().observe(this, categories -> {
            MainActivity.this.categories = categories;
            showOnSpinner();
        });

        viewModel.getBooks().observe(this, books -> {
            MainActivity.this.books = books;
            adapter.setBooks(books);
        });

        binding.setFabClick(() -> {
            Intent intent = new Intent(this, AddEditActivity.class);
            startActivityForResult(intent, ADD_REQUEST);
        });

        binding.secondaryLayout.categorySpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedCategory = (Category) parent.getItemAtPosition(position);
                        viewModel.fetchBooks(selectedCategory.getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Book book = books.get(viewHolder.getAdapterPosition());
                viewModel.deleteBooks(book);
                adapter.deleteBook(book);
            }
        }).attachToRecyclerView(binding.secondaryLayout.rvBooks);
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> adapter =
                new ArrayAdapter<>(this, R.layout.spinner_item, categories);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        binding.setSpinnerAdapter(adapter);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = binding.secondaryLayout.rvBooks;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.setEvent(book -> {
            Intent intent = new Intent(this, AddEditActivity.class);
            intent.putExtra(BOOK, book);
            startActivityForResult(intent, EDIT_REQUEST);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            Book book = (Book) data.getSerializableExtra(BOOK);

            if(requestCode == ADD_REQUEST) {
                book.setCategoryId(selectedCategory.getId());
                viewModel.addNewBook(book);
                adapter.addBook(book);
                return;
            } else if(requestCode == EDIT_REQUEST) {
                viewModel.updateBooks(book);
                adapter.updateBook(book);
                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
