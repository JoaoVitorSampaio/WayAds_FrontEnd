package com.wayads.data.repository

import com.wayads.data.model.AnuncioEstatico
import com.wayads.data.network.ApiService
import javax.inject.Inject

class AnuncioEstaticoRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAnuncioEstatico(): AnuncioEstatico {
        return apiService.getAnuncioEstatico().first()
    }
}
