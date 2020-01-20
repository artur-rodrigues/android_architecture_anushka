package com.androidtutz.anushka.ebookshop.view.event;

import com.androidtutz.anushka.ebookshop.model.entities.Book;

public interface RecyclerViewEvent {
    void onItemClick(Book book);
}
