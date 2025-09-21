package com.wayads.data.model

import com.google.gson.annotations.SerializedName

data class Anuncio(
    @SerializedName("id") val id: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("imagemUrl") val imagemUrl: String,
    @SerializedName("videoUrl") val videoUrl: String?,
    @SerializedName("ativo") val ativo: Boolean,
    @SerializedName("dataInicio") val dataInicio: String,
    @SerializedName("dataFim") val dataFim: String?,
    @SerializedName("prioridade") val prioridade: Int,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("visualizacoes") val visualizacoes: Int,
    @SerializedName("cliques") val cliques: Int
)
