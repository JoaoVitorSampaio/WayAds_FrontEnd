package com.wayads.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Receita(
    @SerializedName("id") val id: Long,
    @SerializedName("nome") val nome: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("ingredientes") val ingredientes: List<String>,
    @SerializedName("modoPreparo") val modoPreparo: List<String>,
    @SerializedName("imagemUrl") val imagemUrl: String,
    @SerializedName("tempoPreparo") val tempoPreparo: String,
    @SerializedName("porcoes") val porcoes: String,
    @SerializedName("nivelDificuldade") val nivelDificuldade: String,
    @SerializedName("criadoEm") val criadoEm: String?,
    @SerializedName("atualizadoEm") val atualizadoEm: String?
) : Serializable
