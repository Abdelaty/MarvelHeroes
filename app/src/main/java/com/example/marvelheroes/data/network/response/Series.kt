package com.example.marvelheroes.data.network.response



data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Items>,
    val returned: Int
)