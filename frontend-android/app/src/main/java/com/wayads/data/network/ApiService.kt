package com.wayads.data.network

import com.wayads.app.data.model.kids.Kids
import com.wayads.app.data.model.turismo.PontoTuristico
import com.wayads.data.model.Anuncio
import com.wayads.data.model.AnuncioEstatico
import com.wayads.data.model.MovieResponse
import com.wayads.data.model.MusicaResponse
import com.wayads.data.model.Noticia
import com.wayads.data.model.PontoGastronomico
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v1/anuncios")
    suspend fun getAnuncios(): List<Anuncio>

    @GET("v1/atualidades/{id}")
    suspend fun getNoticia(@Path("id") id: Int): Noticia

    @GET("v1/atualidades/categoria/{category}")
    suspend fun getNoticias(@Path("category") category: String): List<Noticia>

    @GET("entretenimento")
    suspend fun getEntretenimento(): List<Anuncio>

    @GET("v1/pontos-gastronomicos")
    suspend fun getPontosGastronomicos(): List<PontoGastronomico>

    @GET("kids")
    suspend fun getKids(): List<Anuncio>

    @GET("v1/kids/desenho/{nome}")
    suspend fun getKidVideo(@Path("nome") nome: String): Kids

    @GET("turismo")
    suspend fun getTurismo(): List<Anuncio>

    @GET("v1/filmes")
    suspend fun getMovies(): MovieResponse

    @GET("v1/musicas")
    suspend fun getMusicas(): MusicaResponse

    @GET("v1/anuncios-estaticos")
    suspend fun getAnuncioEstatico(): List<AnuncioEstatico>

    @GET("v1/turismo/categoria/{categoria}")
    suspend fun getPontosTuristicosPorCategoria(@Path("categoria") categoria: String): List<PontoTuristico>

    @GET("v1/turismo/{id}")
    suspend fun getPontoTuristico(@Path("id") id: Long): PontoTuristico
}
