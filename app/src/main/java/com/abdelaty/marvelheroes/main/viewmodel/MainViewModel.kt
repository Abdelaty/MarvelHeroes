package com.abdelaty.marvelheroes.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdelaty.marvelheroes.data.network.response.Result
import com.abdelaty.marvelheroes.main.model.HeroesRepository

class MainViewModel : ViewModel() {
    /**
     * Live Data Instance
     */
    private val heroesRepository =
        HeroesRepository()

    val response: LiveData<List<Result>> get() = heroesRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        heroesRepository.completableJob.cancel()
    }
}
