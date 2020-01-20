package com.androidtutz.anushka.ebookshop.model.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.androidtutz.anushka.ebookshop.model.entities.Book;
import com.androidtutz.anushka.ebookshop.model.entities.Category;
import com.androidtutz.anushka.ebookshop.model.room.dao.BookDAO;
import com.androidtutz.anushka.ebookshop.model.room.dao.CategoryDAO;
import com.androidtutz.anushka.ebookshop.model.room.repository.BooksDatabase;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EBookShopRepository {

    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;

    private LiveData<List<Category>> categories;
    private LiveData<List<Book>> books;

    public EBookShopRepository(Application application) {
        BooksDatabase booksDatabase = BooksDatabase.getInstance(application);

        categoryDAO = booksDatabase.categoryDAO();
        bookDAO = booksDatabase.bookDAO();
    }

    public Disposable insertCategory(Category category) {
        return executeCompletable(() -> categoryDAO.insert(category));
    }

    public Disposable deleteCategory(Category category) {
        return executeCompletable(()-> categoryDAO.delete(category));
    }

    public Disposable updateCategory(Category category) {
        return executeCompletable(()-> categoryDAO.update(category));
    }

    public Disposable getCategories(Consumer<List<Category>> consumer) {
        return executeSingle(() -> categoryDAO.getAllCategories(), consumer);
    }

    public Disposable insertBooks(Book book) {
        return executeCompletable(() -> bookDAO.insert(book));
    }

    public Disposable deleteBooks(Book book) {
        return executeCompletable(() -> bookDAO.delete(book));
    }

    public Disposable updateBooks(Book book) {
        return executeCompletable(() -> bookDAO.update(book));
    }

    public Disposable getBooks(int categoryId, Consumer<List<Book>> consumer) {
        return executeSingle(() -> bookDAO.getBooks(categoryId), consumer);
    }

    private Disposable executeCompletable(Action action) {
        return Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, Throwable::printStackTrace);
    }

    private <S> Disposable executeSingle(Callable<S> callable, Consumer<S> consumer) {
        return Single.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, Throwable::printStackTrace);
    }
}
