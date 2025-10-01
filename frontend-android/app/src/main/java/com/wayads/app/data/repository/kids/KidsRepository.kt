package com.wayads.app.data.repository.kids

import com.wayads.app.data.model.kids.Kids
import com.wayads.data.network.ApiService
import javax.inject.Inject

class KidsRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getKidVideo(nome: String): Kids {
        return apiService.getKidVideo(nome)
    }
}