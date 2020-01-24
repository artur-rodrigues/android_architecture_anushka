package com.androidtutz.anushka.didemo.di.module;

import com.androidtutz.anushka.didemo.model.Battery;
import com.androidtutz.anushka.didemo.model.NickelCadmiumBattery;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class BatteryModule {

    @Binds
    abstract Battery bindNCBattery(NickelCadmiumBattery battery);
}
