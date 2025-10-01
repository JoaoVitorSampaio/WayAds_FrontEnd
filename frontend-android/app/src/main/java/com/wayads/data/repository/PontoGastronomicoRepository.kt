package com.wayads.data.repository

import com.wayads.data.model.PontoGastronomico
import com.wayads.data.network.ApiService
import javax.inject.Inject

class PontoGastronomicoRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPontosGastronomicos(): List<PontoGastronomico> {
        return apiService.getPontosGastronomicos()
    }

    suspend fun getPontosGastronomicosPorCategoria(categoria: String): List<PontoGastronomico> {
        return apiService.getPontosGastronomicosPorCategoria(categoria)
    }

    suspend fun getPontoGastronomico(id: Long): PontoGastronomico {
        return apiService.getPontoGastronomico(id)
    }

}