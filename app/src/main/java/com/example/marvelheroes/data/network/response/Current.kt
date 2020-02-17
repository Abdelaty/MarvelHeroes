package com.example.marvelheroes.data.network.response


import com.google.gson.annotations.SerializedName

data class Current(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)