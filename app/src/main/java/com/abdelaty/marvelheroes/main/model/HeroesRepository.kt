package com.abdelaty.marvelheroes.main.model

import androidx.lifecycle.MutableLiveData
import com.abdelaty.marvelheroes.data.network.MarvelApiService
import com.abdelaty.marvelheroes.data.network.response.Result
import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.*

class HeroesRepository {


    private var heroes = mutableListOf<Result>()
    private var mutableLiveData = MutableLiveData<List<Result>>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private val thisApiCorService by lazy {
        MarvelApiService.invoke()
    }

    fun getMutableLiveData(): MutableLiveData<List<Result>> {
        coroutineScope.launch {
            val request = thisApiCorService.getCharactersResponseAsync()
            withContext(Dispatchers.Main) {
                try {

                    val response = request.await()
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

}