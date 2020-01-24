package com.anushka.androidtutz.contactmanager.di.component;

import com.anushka.androidtutz.contactmanager.MainActivity;
import com.anushka.androidtutz.contactmanager.di.module.AppModule;
import com.anushka.androidtutz.contactmanager.di.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
