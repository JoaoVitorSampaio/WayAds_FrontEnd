package com.wayads.data.repository

import com.wayads.data.model.Musica
import com.wayads.data.network.ApiService
import javax.inject.Inject

class MusicaRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getMusicas(): List<Musica> {
        val response = api.getMusicas()
        return response.content
    }
}