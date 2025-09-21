package com.wayads.data.model

import com.google.gson.annotations.SerializedName

data class Noticia(
    @SerializedName("id") val id: Int,
    @SerializedName("categoria") val categoria: String,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("fotoUrl") val fotoUrl: String,
    @SerializedName("fonte") val fonte: String,
    @SerializedName("linkQr") val linkQr: String,
    @SerializedName("criadoEm") val criadoEm: String
)
