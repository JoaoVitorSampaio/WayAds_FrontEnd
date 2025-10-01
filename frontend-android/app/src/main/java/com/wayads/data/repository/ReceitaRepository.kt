package com.wayads.data.repository

import com.wayads.data.model.Receita
import com.wayads.data.network.ApiService
import javax.inject.Inject

class ReceitaRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getReceitas(): List<Receita> {
        return apiService.getReceitas()
    }

    suspend fun getReceita(id: Long): Receita {
        return apiService.getReceita(id)
    }
}
