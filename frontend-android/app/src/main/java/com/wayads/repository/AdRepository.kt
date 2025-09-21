package com.wayads.repository

import com.wayads.data.model.Ad
import com.wayads.data.network.ApiService
import javax.inject.Inject

class AdRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAnuncios(): List<Ad> {
        return apiService.getAnuncios()
    }
}