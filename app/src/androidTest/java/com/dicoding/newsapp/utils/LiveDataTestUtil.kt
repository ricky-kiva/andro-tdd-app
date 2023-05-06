package com.dicoding.newsapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object LiveDataTestUtil {

    // wait LiveData to get first value
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        // initialized Latch with a count of 1
        // means it needs to be counted down once (to 0) before any thread that is waiting could proceed
        val latch = CountDownLatch(1)

        // set up observer
        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                data = t
                // decrement latch by 1 (1-1 = 0)
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        // observe forever the `observer`
        this.observeForever(observer)

        try {
            // if the data not set after 2 second (latch still 1), it will throw exception
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("LiveData value was never set.")
            }
        } finally {
            this.removeObserver(observer)
        }
        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    // observe livedata until block is finished executed, after that removeObserver
    suspend fun <T> LiveData<T>.observeForTesting(block: suspend () -> Unit) {
        val observer = Observer<T>{}
        try {
            observeForever(observer)
            block()
        } finally {
            removeObserver(observer)
        }
    }

}