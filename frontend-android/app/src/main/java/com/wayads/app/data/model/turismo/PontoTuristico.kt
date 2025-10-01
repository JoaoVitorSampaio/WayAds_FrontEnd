package com.wayads.app.data.model.turismo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PontoTuristico(
    @SerializedName("id")
    val id: Long,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("categoria")
    val categoria: String,
    @SerializedName("urlFotoPrincipal")
    val urlFotoPrincipal: String,
    @SerializedName("urlVideo")
    val urlVideo: String?,
    @SerializedName("cidade")
    val cidade: String,
    @SerializedName("estado")
    val estado: String,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("horarioAbertura")
    val horarioAbertura: String?,
    @SerializedName("horarioFechamento")
    val horarioFechamento: String?,
    @SerializedName("precoEntrada")
    val precoEntrada: Double?,
    @SerializedName("gratuito")
    val gratuito: Boolean
): Serializable
