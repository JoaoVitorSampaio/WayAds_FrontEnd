package com.wayads.data.model

// Representa um Ponto Gastronômico que vem da API
data class PontoGastronomico(
    val id: Long,
    val nome: String,
    val descricao: String,
    val localizacao: String,
    val imagemUrl: String?,
    val categoria: String,
    val fonte: String?,
    val criadoEm: String?, // Datas podem vir como String da API
    val atualizadoEm: String?
)