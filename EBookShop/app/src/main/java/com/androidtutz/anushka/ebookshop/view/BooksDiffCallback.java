package com.androidtutz.anushka.ebookshop.view;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.androidtutz.anushka.ebookshop.model.entities.Book;

import java.util.List;

public class BooksDiffCallback extends DiffUtil.Callback {

    List<Book> oldBooks;
    List<Book> newBooks;

    public BooksDiffCallback(List<Book> oldBooks, List<Book> newBooks) {
        this.oldBooks = oldBooks;
        this.newBooks = newBooks;
    }

    @Override
    public int getOldListSize() {
        return oldBooks == null ? 0 : oldBooks.size();
    }

    @Override
    public int getNewListSize() {
        return newBooks == null ? 0 : newBooks.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        return oldBooks.get(oldPosition).getBookId() == newBooks.get(newPosition).getBookId();
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        return oldBooks.get(oldPosition).equals(newBooks.get(newPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
