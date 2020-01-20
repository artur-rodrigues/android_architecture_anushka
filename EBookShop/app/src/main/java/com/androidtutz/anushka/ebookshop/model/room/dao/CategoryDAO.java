package com.androidtutz.anushka.ebookshop.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.androidtutz.anushka.ebookshop.model.entities.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM categories_table")
    List<Category> getAllCategories();
}
