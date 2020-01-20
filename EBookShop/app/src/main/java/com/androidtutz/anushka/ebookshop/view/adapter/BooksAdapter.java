package com.androidtutz.anushka.ebookshop.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidtutz.anushka.ebookshop.R;
import com.androidtutz.anushka.ebookshop.databinding.BookListItemBinding;
import com.androidtutz.anushka.ebookshop.model.entities.Book;
import com.androidtutz.anushka.ebookshop.view.BooksDiffCallback;
import com.androidtutz.anushka.ebookshop.view.event.RecyclerViewEvent;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private RecyclerViewEvent event;

    private List<Book> books;

    private int lastUpdatePosition = -1;

    public BooksAdapter() {}

    public BooksAdapter(RecyclerViewEvent event, List<Book> books) {
        this.event = event;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BookListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.book_list_item,
                viewGroup,
                false
        );
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
        bookViewHolder.binding.setBook(books.get(i));
    }

    @Override
    public int getItemCount() {
        return this.books == null ? 0 : this.books.size();
    }

    public void setEvent(RecyclerViewEvent event) {
        this.event = event;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();

        /*final DiffUtil.DiffResult result = DiffUtil
                .calculateDiff(new BooksDiffCallback(books, this.books));

        result.dispatchUpdatesTo(this);
        this.books = books;*/
    }

    public void addBook(Book book) {
        this.books.add(book);
        notifyDataSetChanged();
    }

    public void updateBook(Book book) {
        this.books.set(lastUpdatePosition, book);
        notifyDataSetChanged();
        lastUpdatePosition = -1;
    }

    public void deleteBook(Book book) {
        this.books.remove(book);
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        private BookListItemBinding binding;

        public BookViewHolder(@NonNull BookListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(view -> {
                int clickedPosition = getAdapterPosition();
                if(event != null && clickedPosition != RecyclerView.NO_POSITION) {
                    event.onItemClick(books.get(clickedPosition));
                    lastUpdatePosition = clickedPosition;
                }
            });
        }
    }
}
