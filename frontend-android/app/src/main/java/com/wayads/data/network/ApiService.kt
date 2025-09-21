package com.wayads.data.network

import com.wayads.data.model.Anuncio
import com.wayads.data.model.MovieResponse
import retrofit2.http.GET

interface ApiService {

    @GET("v1/anuncios")
    suspend fun getAnuncios(): List<Anuncio>

    @GET("atualidades")
    suspend fun getAtualidades(): List<Anuncio>

    @GET("entretenimento")
    suspend fun getEntretenimento(): List<Anuncio>

    @GET("gastronomia")
    suspend fun getGastronomia(): List<Anuncio>

    @GET("kids")
    suspend fun getKids(): List<Anuncio>

    @GET("turismo")
    suspend fun getTurismo(): List<Anuncio>

    @GET("v1/filmes")
    suspend fun getMovies(): MovieResponse
}