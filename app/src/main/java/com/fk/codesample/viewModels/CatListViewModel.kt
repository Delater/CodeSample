package com.fk.codesample.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fk.codesample.entities.Cat
import com.fk.codesample.network.repository.CatRepository
import com.fk.codesample.util.AppSchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val catRepository: CatRepository,
    private val schedulers: AppSchedulers
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val stateLiveData = MutableLiveData<State>(State.Loading)

    fun fetchCats() {
        catRepository.getCats()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .subscribe({ catList ->
                stateLiveData.value = State.Content(catList)
            }, {
                stateLiveData.value = State.Error
                Log.e(CatListViewModel::class.java.name, it.message.toString())
            }).apply { compositeDisposable.add(this) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    sealed class State {
        object Loading : State()
        data class Content(val catList: List<Cat>) : State()
        object Error : State()
    }
}