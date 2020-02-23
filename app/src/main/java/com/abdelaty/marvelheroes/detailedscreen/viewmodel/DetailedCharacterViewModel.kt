package com.abdelaty.marvelheroes.detailedscreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdelaty.marvelheroes.data.network.response.Result
import com.abdelaty.marvelheroes.detailedscreen.model.DetailedHeroRepository

class DetailedCharacterViewModel(mExtra: String) : ViewModel() {

    /**
     * Live Data Instance
     */
    private val heroRepository =
        DetailedHeroRepository(
            mExtra
        )

    val response: LiveData<List<Result>> get() = heroRepository.getMutableLiveData()
    val eventsResponse: LiveData<List<Result>> get() = heroRepository.getEventsMutableLiveData()
    val seriesResponse: LiveData<List<Result>> get() = heroRepository.getSeriesMutableLiveData()
    val storiesResponse: LiveData<List<Result>> get() = heroRepository.getStoriesMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        heroRepository.completableJob.cancel()
    }
}