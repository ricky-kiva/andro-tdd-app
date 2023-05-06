package com.dicoding.newsapp.ui.list

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.newsapp.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import com.dicoding.newsapp.data.remote.retrofit.ApiConfig
import com.dicoding.newsapp.utils.EspressoIdlingResource
import com.dicoding.newsapp.utils.JsonConverter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class NewsFragmentTest {

    // instantiate MockWebServer()
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        // mock a Mock server
        mockWebServer.start(8080)
        ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
        // register to EspressoIdlingResource (that tells whether an app is busy or not)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        // turn-off Mock server
        mockWebServer.shutdown()
        // unregister to EspressoIdlingResource (that tells whether an app is busy or not)
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun getHeadlineNews_Success() {
        // test behavior of NewsFragment when launched with NewsFragment.TAB_NEWS Argument
        val bundle = Bundle()
        bundle.putString(NewsFragment.ARG_TAB, NewsFragment.TAB_NEWS)
        launchFragmentInContainer<NewsFragment>(bundle, R.style.Theme_News)

        // set MockServer Response from JSON
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse) // enqueue the Mock Response

        // checks whether the recyclerView is displayed
        onView(withId(R.id.rv_news))
            .check(matches(isDisplayed()))

        // checks whether the string is visible on device screen
        onView(withText("Inti Bumi Mendingin Lebih Cepat, Pertanda Apa? - detikInet"))
            .check(matches(isDisplayed()))

        // checks whether the string is visible on device screen after scrolling the recyclerView
        onView(withId(R.id.rv_news))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Perjalanan Luar Angkasa Sebabkan Anemia - CNN Indonesia"))
                )
            )
    }

    @Test
    fun getHeadlineNews_Error() {
        // test behavior of NewsFragment when launched with NewsFragment.TAB_NEWS Argument
        val bundle = Bundle()
        bundle.putString(NewsFragment.ARG_TAB, NewsFragment.TAB_NEWS)
        launchFragmentInContainer<NewsFragment>(bundle, R.style.Theme_News)

        // set MockServer Response
        val mockResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        // checks whether the View is visible on device screen
        onView(withId(R.id.tv_error))
            .check(matches(isDisplayed()))

        // checks whether the string is visible on device screen
        onView(withText("Oops.. something went wrong."))
            .check(matches(isDisplayed()))
    }

}