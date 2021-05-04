package com.anushka.didemo

import android.util.Log.i
import dagger.Module
import dagger.Provides

@Module
class MemoryCarModule(val memorySize: Int) {

    @Provides
    fun provideMemoryCard(): MemoryCard {
        i("MYTAG", "Size of memory $memorySize")
        return MemoryCard()
    }
}