package com.anushka.didemo

import android.app.Application

class SmartPhoneApplication : Application() {

    lateinit var smartPhoneComponent: SmartPhoneComponent

    override fun onCreate() {
        smartPhoneComponent = initDagger()
        super.onCreate()
    }

    private fun initDagger(): SmartPhoneComponent {
        return DaggerSmartPhoneComponent.builder()
            .memoryCarModule(MemoryCarModule(1000))
            .build()
    }
}