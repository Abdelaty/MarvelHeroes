package com.abdelaty.marvelheroes.data.network

import com.abdelaty.marvelheroes.data.network.response.MarvelApiResponse
import com.abdelaty.marvelheroes.utility.Constants.Companion.API_PRIVATE_KEY
import com.abdelaty.marvelheroes.utility.Constants.Companion.API_PUBLIC_KEY
import com.abdelaty.marvelheroes.utility.Constants.Companion.BASE_URL
import com.abdelaty.marvelheroes.utility.Constants.Companion.HASH
import com.abdelaty.marvelheroes.utility.Constants.Companion.TIME_STMP
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.math.BigInteger
import java.security.MessageDigest

interface MarvelApiService {
    @GET("characters")
    fun getCharactersResponseAsync(
    ): Deferred<MarvelApiResponse>

    @GET("characters/{characterId}/comics")
    fun getComicsCharacterAsync(
        @Path("characterId") characterId: String
    ): Deferred<MarvelApiResponse>

    @GET("characters/{characterId}/events")
    fun getEventsCharacterAsync(
        @Path("characterId") characterId: String
    ): Deferred<MarvelApiResponse>

    @GET("characters/{characterId}/stories")
    fun getStoriesCharacterAsync(
        @Path("characterId") characterId: String
    ): Deferred<MarvelApiResponse>

    @GET("characters/{characterId}/series")
    fun getSeriesCharacterAsync(
        @Path("characterId") characterId: String
    ): Deferred<MarvelApiResponse>

    companion object {
        operator fun invoke(): MarvelApiService {
            TIME_STMP = System.currentTimeMillis().toString()
            HASH = (TIME_STMP + API_PRIVATE_KEY + API_PUBLIC_KEY)
            HASH = HASH.md5()
            val requestInterceptor =
                Interceptor { chain ->
                    val url =
                        chain.request().url().newBuilder().addQueryParameter("ts", TIME_STMP)
                            .addQueryParameter("apikey", API_PUBLIC_KEY)
                            .addQueryParameter("hash", HASH)
                            .build()
                    val request = chain.request().newBuilder().url(url).build()
                    return@Interceptor chain.proceed(request)

                }
            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()
            return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(MarvelApiService::class.java)
        }


    }


}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}