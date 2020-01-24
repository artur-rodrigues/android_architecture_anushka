package com.androidtutz.anushka.didemo.di.component;

import com.androidtutz.anushka.didemo.di.module.AppModule;
import com.androidtutz.anushka.didemo.di.module.BatteryModule;
import com.androidtutz.anushka.didemo.di.module.MemoryCardModule;
import com.androidtutz.anushka.didemo.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, MemoryCardModule.class, BatteryModule.class})
public interface SmartPhoneComponent {
//    SmartPhone getSmartPhone();

    void inject(MainActivity mainActivity);
}
