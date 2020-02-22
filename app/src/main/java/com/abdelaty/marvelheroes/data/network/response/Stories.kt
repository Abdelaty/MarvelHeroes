package com.abdelaty.marvelheroes.data.network.response



data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<Items>,
    val returned: Int
)