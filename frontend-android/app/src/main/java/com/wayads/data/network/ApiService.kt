package com.wayads.data.network

import com.wayads.data.model.Ad
import com.wayads.data.model.MovieResponse
import retrofit2.http.GET

interface ApiService {

    @GET("anuncios")
    suspend fun getAnuncios(): List<Ad>

    @GET("atualidades")
    suspend fun getAtualidades(): List<Ad>

    @GET("entretenimento")
    suspend fun getEntretenimento(): List<Ad>

    @GET("gastronomia")
    suspend fun getGastronomia(): List<Ad>

    @GET("kids")
    suspend fun getKids(): List<Ad>

    @GET("turismo")
    suspend fun getTurismo(): List<Ad>

    @GET("v1/filmes")
    suspend fun getMovies(): MovieResponse
}