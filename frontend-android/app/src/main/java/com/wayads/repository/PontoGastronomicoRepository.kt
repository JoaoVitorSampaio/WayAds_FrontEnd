package com.wayads.repository

import com.wayads.data.model.PontoGastronomico
import com.wayads.data.network.ApiService
import javax.inject.Inject

class PontoGastronomicoRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPontosGastronomicos(): List<PontoGastronomico> {
        return apiService.getPontosGastronomicos()
    }

}