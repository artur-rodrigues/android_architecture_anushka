package com.androidtutz.anushka.ebookshop.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EBookShopRepository {

    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;

    private LiveData<List<Category>> categories;
    private LiveData<List<Book>> books;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EBookShopRepository(Application application) {
        BooksDatabase booksDatabase = BooksDatabase.getInstance(application);

        categoryDAO = booksDatabase.categoryDAO();
        bookDAO = booksDatabase.bookDAO();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<Book>> getBooks(int categoryId) {
        return bookDAO.getBooks(categoryId);
    }




    public void insertCategory(Category category) {
        executeAction(() -> categoryDAO.insert(category));
    }

    public void deleteCategory(Category category) {
        executeAction(()-> categoryDAO.delete(category));
    }

    public void updateCategory(Category category) {
        executeAction(()-> categoryDAO.update(category));
    }



    public void insertBooks(Book book) {
        executeAction(() -> bookDAO.insert(book));
    }

    public void deleteBooks(Book book) {
        executeAction(() -> bookDAO.delete(book));
    }

    public void updateBooks(Book book) {
        executeAction(() -> bookDAO.update(book));
    }


    private void executeAction(Action action) {
        Disposable disposable = Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    private <S> void executeCallable(Callable<S> callable, Consumer<S> consumer) {
        Disposable disposable = Single.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer,
                        throwable -> {

                        });
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if(! compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
            compositeDisposable.dispose();
        }
    }

    /*private Completable getCompletable(Action action) {
        return Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    /*private Single getSingle(Callable callable) {
        return Single.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/
}
