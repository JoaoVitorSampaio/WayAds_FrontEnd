package com.wayads.data.model

data class Movie(
    val title: String,
    val posterUrl: String,
    val description: String
)

data class MovieResponse(val content: List<Movie>)