package com.example.marvelheroes.data.network.response


import com.google.gson.annotations.SerializedName

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)