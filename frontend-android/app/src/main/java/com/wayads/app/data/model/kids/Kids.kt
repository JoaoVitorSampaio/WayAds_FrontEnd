package com.wayads.app.data.model.kids

import com.google.gson.annotations.SerializedName

data class Kids(
    @SerializedName("id")
    val id: Long,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("videoUrl")
    val videoUrl: String,
    @SerializedName("criadoEm")
    val criadoEm: String
)