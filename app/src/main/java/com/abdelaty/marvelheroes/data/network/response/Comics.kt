package com.abdelaty.marvelheroes.data.network.response


data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Items>,
    val returned: Int
)