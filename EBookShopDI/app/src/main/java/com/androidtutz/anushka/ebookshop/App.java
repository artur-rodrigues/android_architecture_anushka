package com.androidtutz.anushka.ebookshop;

import android.app.Application;

import com.androidtutz.anushka.ebookshop.di.component.AppComponent;
import com.androidtutz.anushka.ebookshop.di.component.DaggerAppComponent;
import com.androidtutz.anushka.ebookshop.di.modules.AppModule;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
