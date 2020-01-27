package com.example.activitystatesdemo.component

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.activitystatesdemo.utils.log

class DemoAppComponent(private val activityName: String): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        log("DemoAppComponent onCreated() invoked for $activityName")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        log("DemoAppComponent onStart() invoked for $activityName")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        log("DemoAppComponent onResume() invoked for $activityName")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        log("DemoAppComponent onPause() invoked for $activityName")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        log("DemoAppComponent onStop() invoked for $activityName")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        log("DemoAppComponent onDestroy() invoked for $activityName")
    }
}