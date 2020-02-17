package com.example.marvelheroes.data.network

import com.example.marvelheroes.data.network.response.CurrentHeroesResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "4f760508310c5acec930c84fcdaac985"
const val BASE_URL = "http://api.weatherstack.com"

//http://api.weatherstack.com/current?access_key=4f760508310c5acec930c84fcdaac985&query=New%20York
interface WeatherApiService {
    @GET("current")
    fun getCurrentWeatherAsync(
        @Query("query") location: String,
        @Query("units") units: String
    ): Deferred<CurrentHeroesResponse>

    companion object {
        operator fun invoke(): WeatherApiService {
            val requestInterceptor =
                Interceptor { chain ->
                    val url =
                        chain.request().url().newBuilder().addQueryParameter("access_key", API_KEY)
                            .build()
                    val request = chain.request().newBuilder().url(url).build()
                    return@Interceptor chain.proceed(request)

                }
            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()
            return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(WeatherApiService::class.java)
        }
    }
}