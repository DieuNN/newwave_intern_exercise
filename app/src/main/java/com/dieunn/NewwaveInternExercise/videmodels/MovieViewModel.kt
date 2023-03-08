package com.dieunn.NewwaveInternExercise.videmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieunn.NewwaveInternExercise.RetrofitInstance
import com.dieunn.NewwaveInternExercise.models.Movie
import kotlinx.coroutines.*

class MovieViewModel : ViewModel() {
    // lazy is when we need it, it will be created,
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    // current loaded page, default 1
    val currentPage: MutableLiveData<Int> by lazy {
        MutableLiveData(1)
    }

    // list of movies has been loaded, default empty
    val data: MutableLiveData<Set<Movie>> by lazy {
        MutableLiveData(emptySet())
    }


    suspend fun initData() = viewModelScope.launch {
        data.postValue(getData(1))
    }

    // get data from api
    private suspend fun getData(page: Int) = RetrofitInstance.api.getResponse(page = page).results

    // next page
    fun nextPage() {
        this.currentPage.postValue(this.currentPage.value?.plus(1))
    }

    fun loadMore() = viewModelScope.launch {
        nextPage()
        data.postValue(data.value?.toMutableSet()?.apply {
            print(this.size)
            addAll(getData(currentPage.value!!))
        })
    }


    fun refreshData() = viewModelScope.launch {
        currentPage.postValue(1)
        initData()
    }


}