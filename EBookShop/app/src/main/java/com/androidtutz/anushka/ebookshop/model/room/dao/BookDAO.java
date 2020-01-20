package com.androidtutz.anushka.ebookshop.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.androidtutz.anushka.ebookshop.model.entities.Book;

import java.util.List;
@Dao
public interface BookDAO {

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books_table")
    List<Book> getAllBooks();

    @Query("SELECT * FROM books_table WHERE category_id==:categoryId")
    List<Book> getBooks(int categoryId);

}
