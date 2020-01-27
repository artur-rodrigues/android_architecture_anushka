package com.androidtutz.anushka.ebookshop.di.component;

import com.androidtutz.anushka.ebookshop.di.modules.AppModule;
import com.androidtutz.anushka.ebookshop.di.modules.RepositoryModule;
import com.androidtutz.anushka.ebookshop.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
