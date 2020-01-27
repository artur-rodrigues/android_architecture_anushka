package com.androidtutz.anushka.ebookshop.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.androidtutz.anushka.ebookshop.model.EBookShopRepository;
import com.androidtutz.anushka.ebookshop.viewmodel.MainActivityViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@SuppressWarnings("ALL")
@Singleton
public class MainViewModelFactory implements ViewModelProvider.Factory {

    private EBookShopRepository repository;

    @Inject
    public MainViewModelFactory(EBookShopRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(repository);
        }
        throw new IllegalArgumentException("Trolhei!!!");
    }
}
