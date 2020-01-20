package com.androidtutz.anushka.ebookshop.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.androidtutz.anushka.ebookshop.R;
import com.androidtutz.anushka.ebookshop.databinding.ActivityAddEditBinding;
import com.androidtutz.anushka.ebookshop.model.entities.Book;

public class AddEditActivity extends AppCompatActivity {

    public static final String BOOK = "book";

    private ActivityAddEditBinding binding;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        book = (Book) getIntent().getSerializableExtra(BOOK);

        if(book == null) {
            book = new Book();
            setTitle("Add New Book");
        } else {
            setTitle("Edit Book");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit);
        binding.setBook(book);

        binding.setSubmit(() -> {
            if (TextUtils.isEmpty(book.getBookName()) ||
                TextUtils.isEmpty(book.getUnitPrice())) {
                makeToast("Fields can not be null");
            } else {
                Intent intent = new Intent();
                intent.putExtra(BOOK, book);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
