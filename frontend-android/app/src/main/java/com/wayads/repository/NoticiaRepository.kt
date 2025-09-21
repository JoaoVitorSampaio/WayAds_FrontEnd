package com.wayads.repository

import com.wayads.data.model.Noticia
import com.wayads.data.network.ApiService
import javax.inject.Inject

class NoticiaRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNoticias(category: String): List<Noticia> {
        return apiService.getNoticias(category)
    }

    suspend fun getNoticia(id: Int): Noticia {
        return apiService.getNoticia(id)
    }
}
