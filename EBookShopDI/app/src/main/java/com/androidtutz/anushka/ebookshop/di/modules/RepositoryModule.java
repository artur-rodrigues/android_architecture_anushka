package com.androidtutz.anushka.ebookshop.di.modules;

import android.content.Context;

import com.androidtutz.anushka.ebookshop.model.BookDAO;
import com.androidtutz.anushka.ebookshop.model.BooksDatabase;
import com.androidtutz.anushka.ebookshop.model.CategoryDAO;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    BooksDatabase providesDatabase(Context context) {
        return BooksDatabase.getInstance(context);
    }

    @Provides
    CategoryDAO providesCategoryDao(BooksDatabase database) {
        return database.categoryDAO();
    }

    @Provides
    BookDAO providesBookDao(BooksDatabase database) {
        return database.bookDAO();
    }
}
