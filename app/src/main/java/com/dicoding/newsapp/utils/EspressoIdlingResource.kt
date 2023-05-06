package com.dicoding.newsapp.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    // CountingIdlingResource used to track whether the app is currently busy / idle (thread-safe)
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        // increment used to tell the app is busy
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            // decrement is used to tell the app is idle
            countingIdlingResource.decrement()
        }
    }

    // utility function that takes lambda function as parameter that will be executed
    inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
        increment() // set app as busy
        return try {
            function()
        } finally {
            decrement() // set app as idle
        }
    }

}