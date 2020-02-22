package com.abdelaty.marvelheroes.detailedscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.abdelaty.marvelheroes.data.network.MarvelApiService
import com.abdelaty.marvelheroes.data.network.response.Result
import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.*

class DetailedHeroRepository(private var heroId: String) {

    private var heroes = mutableListOf<Result>()
    private var events = mutableListOf<Result>()
    private var stories = mutableListOf<Result>()
    private var series = mutableListOf<Result>()

    private var mutableLiveData = MutableLiveData<List<Result>>()
    private var mutableEventsLiveData = MutableLiveData<List<Result>>()
    private var mutableSeriesLiveData = MutableLiveData<List<Result>>()
    private var mutableStoriesLiveData = MutableLiveData<List<Result>>()

    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)
    private val thisApiCorService by lazy {
        MarvelApiService.invoke()
    }

    fun getMutableLiveData(): MutableLiveData<List<Result>> {
        Log.e("getMutableLiveData", heroId)
        coroutineScope.launch {
            val comicsRequest = thisApiCorService.getComicsCharacterAsync(heroId)
            withContext(Dispatchers.Main) {
                try {
                    val response = comicsRequest.await()
                    heroes = response.data.results.toMutableList()
                    mutableLiveData.value = heroes
                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return mutableLiveData
    }

    fun getEventsMutableLiveData(): MutableLiveData<List<Result>> {
        coroutineScope.launch {
            val eventsRequest = thisApiCorService.getEventsCharacterAsync(heroId)
            withContext(Dispatchers.Main) {
                try {
                    val response = eventsRequest.await()
                    events = response.data.results.toMutableList()
                    mutableEventsLiveData.value = events
                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return mutableEventsLiveData
    }

    fun getSeriesMutableLiveData(): MutableLiveData<List<Result>> {
        coroutineScope.launch {

            val seriesRequest = thisApiCorService.getSeriesCharacterAsync(heroId)

            withContext(Dispatchers.Main) {
                try {
                    val response = seriesRequest.await()
                    series = response.data.results.toMutableList()
                    mutableSeriesLiveData.value = series
                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return mutableSeriesLiveData
    }

    fun getStoriesMutableLiveData(): MutableLiveData<List<Result>> {
        coroutineScope.launch {

            val storiesRequest = thisApiCorService.getStoriesCharacterAsync(heroId)

            withContext(Dispatchers.Main) {
                try {
                    val response = storiesRequest.await()
                    stories = response.data.results.toMutableList()
                    mutableStoriesLiveData.value = stories
                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return mutableStoriesLiveData
    }
}