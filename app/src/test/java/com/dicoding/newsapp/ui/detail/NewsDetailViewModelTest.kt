package com.dicoding.newsapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.utils.DataDummy
import com.dicoding.newsapp.utils.LiveDataTestUtil.getOrAwaitValue
import com.dicoding.newsapp.utils.MainDispatcherRule
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsDetailViewModel: NewsDetailViewModel
    private val dummyDetailNews = DataDummy.generateDummyNewsEntity()[0]

    @Before
    fun setup() {
        newsDetailViewModel = NewsDetailViewModel(newsRepository)
        newsDetailViewModel.setNewsData(dummyDetailNews)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when bookmarkStatus false should call saveNews`() = runTest {
        // 1st - 3rd line -> set `Mock Implementation` for `isNewsBookmarked()` of `newsRepository` object
        val expectedBoolean = MutableLiveData<Boolean>()
        expectedBoolean.value = false
        `when`(newsRepository.isNewsBookmarked(dummyDetailNews.title)).thenReturn(expectedBoolean)

        // calls mocked `bookmarkStatus` & should return `expectedBoolean`
        // note: bookmarkStatus call `isNewsBookmarked` from `newsRepository`
        newsDetailViewModel.bookmarkStatus.getOrAwaitValue()

        // calls changeBookmark using dummyDetailNews
        newsDetailViewModel.changeBookmark(dummyDetailNews)

        // this verifies whether saveNews() called during the test
        Mockito.verify(newsRepository).saveNews(dummyDetailNews)
    }

    @Test
    fun `when bookmarkStatus true should call deleteNews`() = runTest {
        // 1st - 3rd line -> set `Mock Implementation` for `isNewsBookmarked()` of `newsRepository` object
        val expectedBoolean = MutableLiveData<Boolean>()
        expectedBoolean.value = true
        `when`(newsRepository.isNewsBookmarked(dummyDetailNews.title)).thenReturn(expectedBoolean)

        // calls mocked `bookmarkStatus` & should return `expectedBoolean`
        // note: bookmarkStatus call `isNewsBookmarked` from `newsRepository`
        newsDetailViewModel.bookmarkStatus.getOrAwaitValue()
        newsDetailViewModel.changeBookmark(dummyDetailNews)

        // this verifies whether deleteNews() called during the test
        Mockito.verify(newsRepository).deleteNews(dummyDetailNews.title)
    }
}