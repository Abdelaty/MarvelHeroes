package com.abdelaty.marvelheroes.data.network.response



data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Items>,
    val returned: Int
)