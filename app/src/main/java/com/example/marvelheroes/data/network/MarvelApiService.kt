package com.example.marvelheroes.data.network

import android.util.Log
import com.example.marvelheroes.data.network.response.MarvelApiResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.math.BigInteger
import java.security.MessageDigest

const val API_PUBLIC_KEY = "ebd094990a30b40e97ab5a73fbf86af8"
const val API_PRIVATE_KEY = "5cfb621f5407c5143a916c86bb0b674596246a62"
var TIME_STMP = "0"
var HASH = ""
const val BASE_URL = "https://gateway.marvel.com/v1/public/"

interface MarvelApiService {
    @GET("characters")
    fun getCharactersRespnoseAsync(
//        @Query("query") location: String,
//        @Query("units") units: String
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
                    Log.e("URL", url.toString())
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