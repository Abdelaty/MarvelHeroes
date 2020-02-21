package com.example.marvelheroes.data.network.response

import java.io.Serializable


data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val title: String,
    val thumbnail: Thumbnail,
    val urls: List<Url>
) : Serializable
