package com.wayads.data.model

import com.google.gson.annotations.SerializedName

data class AnuncioEstatico(
    @SerializedName("id") val id: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("imagemUrl") val imagemUrl: String
)
