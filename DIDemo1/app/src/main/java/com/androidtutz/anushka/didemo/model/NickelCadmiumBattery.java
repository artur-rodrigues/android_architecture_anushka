package com.androidtutz.anushka.didemo.model;

import android.util.Log;

import javax.inject.Inject;

public class NickelCadmiumBattery implements Battery {

    private static final String TAG = "SmartPhone";

    @Inject
    public NickelCadmiumBattery() {}

    @Override
    public void showType() {
        Log.d(TAG, "Nickel Battery");
    }
}
