package com.wayads.data.model

import com.google.gson.annotations.SerializedName

data class Musica(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("posterUrl") val posterUrl: String,
    @SerializedName("artistName") val artistName: String?,
    @SerializedName("audioUrl") val audioUrl: String,
    @SerializedName("createdAt") val createdAt: String
)
