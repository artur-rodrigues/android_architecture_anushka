package com.androidtutz.anushka.didemo;

import android.app.Application;

import com.androidtutz.anushka.didemo.di.component.DaggerSmartPhoneComponent;
import com.androidtutz.anushka.didemo.di.component.SmartPhoneComponent;
import com.androidtutz.anushka.didemo.di.module.AppModule;
import com.androidtutz.anushka.didemo.di.module.MemoryCardModule;

public class App extends Application {

    private SmartPhoneComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerSmartPhoneComponent.builder()
                .appModule(new AppModule(this))
                .memoryCardModule(new MemoryCardModule(100))
                .build();
    }

    public SmartPhoneComponent getComponent() {
        return component;
    }
}
