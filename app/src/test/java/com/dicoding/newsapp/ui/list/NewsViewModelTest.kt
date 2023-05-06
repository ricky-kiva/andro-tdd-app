package com.dicoding.newsapp.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.utils.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import com.dicoding.newsapp.data.Result
import org.junit.Rule
import org.mockito.Mockito

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    // this allows LiveData to be tested synchronously
    @get:Rule
    // swaps background executor to synchronous one
    // it allow the test to observe livedata objects synchronously & assert value that are `emitted` by LiveData
    val instantExecutorRule = InstantTaskExecutorRule()

    // set list of object to mock
    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModel: NewsViewModel
    private val dummyNews = DataDummy.generateDummyNewsEntity()

    @Before
    fun setup() {
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `when getHeadlineNews Should Not Null and Return Success`() {

        // make empty observer that can be used to observe change in LiveData
        val observer = Observer<Result<List<NewsEntity>>>{}

        try {
            // 1st - 3rd line -> set `Mock Implementation` for `getHeadlineNews` of `newsRepository` object
            // set expectedNews data as MutableLiveData instance
            val expectedNews = MutableLiveData<Result<List<NewsEntity>>>()
            // set expectedNews value by typecasting to Result.Success()
            expectedNews.value = Result.Success(dummyNews)
            // use `when()` to mock behavior of `getHeadlineNews()` and tells Mockito to return expectedNews
            `when`(newsRepository.getHeadlineNews()).thenReturn(expectedNews)

            // calls mocked `getHeadlineNews()` & should return `expectedNews`
            // it also starts observing the object
            val actualNews = newsViewModel.getHeadlineNews().observeForever(observer)

            // this verifies whether getHeadlineNews() called during the test
            Mockito.verify(newsRepository).getHeadlineNews()
            Assert.assertNotNull(actualNews)
        } finally {
            // remove observer from LiveData after test finished
            newsViewModel.getHeadlineNews().removeObserver(observer)
        }
    }
}