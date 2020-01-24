package com.anushka.androidtutz.contactmanager;

import android.app.Application;

import com.anushka.androidtutz.contactmanager.di.component.AppComponent;
import com.anushka.androidtutz.contactmanager.di.component.DaggerAppComponent;
import com.anushka.androidtutz.contactmanager.di.module.AppModule;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return this.component;
    }
}