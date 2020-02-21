package com.example.marvelheroes.mainscreen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.marvelheroes.data.network.response.Result

class MainViewModel : ViewModel() {
    /**
     * Live Data Instance
     */
    private val heroesRepository = HeroesRepository()

    val response: LiveData<List<Result>> get() = heroesRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        heroesRepository.completableJob.cancel()
    }
}
