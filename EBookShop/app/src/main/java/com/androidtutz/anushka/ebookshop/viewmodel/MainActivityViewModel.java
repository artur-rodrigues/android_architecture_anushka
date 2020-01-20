package com.androidtutz.anushka.ebookshop.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.androidtutz.anushka.ebookshop.model.entities.Book;
import com.androidtutz.anushka.ebookshop.model.entities.Category;
import com.androidtutz.anushka.ebookshop.model.room.EBookShopRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends AndroidViewModel {

    private EBookShopRepository repository;
    private MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private MutableLiveData<List<Book>> books = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        repository = new EBookShopRepository(application);
    }

    public void fetchCategories() {
        disposable.add(repository.getCategories(
                categories -> MainActivityViewModel.this.categories.setValue(categories)));
    }

    public void fetchBooks(int bookId) {
        disposable.add(repository.getBooks(bookId, books -> MainActivityViewModel.this.books.setValue(books)));
    }

    public void addNewBook(Book book) {
        disposable.add(repository.insertBooks(book));
    }

    public void updateBooks(Book book) {
        disposable.add(repository.updateBooks(book));
    }

    public void deleteBooks(Book book) {
        disposable.add(repository.deleteBooks(book));
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!disposable.isDisposed()) {
            disposable.dispose();
            disposable.clear();
        }
    }
}
