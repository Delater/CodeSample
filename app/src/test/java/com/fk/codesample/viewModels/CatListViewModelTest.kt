package com.fk.codesample.viewModels

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fk.codesample.TestScheduler
import com.fk.codesample.entities.Cat
import com.fk.codesample.network.repository.CatRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class CatListViewModelTest {
    private val scheduler = TestScheduler()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var catRepository: CatRepository

    lateinit var catListViewModel: CatListViewModel

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        MockKAnnotations.init(this)
        catListViewModel = CatListViewModel(catRepository, scheduler)
    }


    @Test
    fun `loading as initial state`() {
        assertEquals(CatListViewModel.State.Loading, catListViewModel.stateLiveData.value)
    }

    @Test
    fun `sets error state if exception is thrown while retrieving cats`() {
        every { catRepository.getCats() } returns Single.error(IOException())

        catListViewModel.fetchCats()

        assertEquals(CatListViewModel.State.Error, catListViewModel.stateLiveData.value)
    }

    @Test
    fun `returns same user data in state as from repository`() {
        every { catRepository.getCats() } returns Single.just(defaultCatList)

        catListViewModel.fetchCats()

        assertEquals(
            defaultCatList,
            (catListViewModel.stateLiveData.value as CatListViewModel.State.Content).catList
        )
    }


    private val defaultCat = Cat("123", "https://google.com", "European", "Hungry", "Europe")

    private val defaultCatList = listOf(defaultCat, defaultCat)
}