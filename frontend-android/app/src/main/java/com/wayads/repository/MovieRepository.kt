package com.wayads.repository

import com.wayads.data.model.Movie
import com.wayads.data.network.ApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovies(): List<Movie> {
        return apiService.getMovies().content
    }
}