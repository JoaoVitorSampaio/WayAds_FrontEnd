package com.wayads.data.repository.turismo

import com.wayads.app.data.model.turismo.PontoTuristico
import com.wayads.data.network.ApiService
import javax.inject.Inject

class TurismoRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPontosTuristicosPorCategoria(categoria: String): List<PontoTuristico> {
        return apiService.getPontosTuristicosPorCategoria(categoria)
    }

    suspend fun getPontoTuristico(id: Long): PontoTuristico {
        return apiService.getPontoTuristico(id)
    }
}
