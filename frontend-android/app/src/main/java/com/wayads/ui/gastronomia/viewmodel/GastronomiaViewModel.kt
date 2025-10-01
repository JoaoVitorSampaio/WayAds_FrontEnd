package com.wayads.ui.gastronomia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.data.model.PontoGastronomico
import com.wayads.data.repository.PontoGastronomicoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GastronomiaViewModel @Inject constructor(
    private val repository: PontoGastronomicoRepository
) : ViewModel() {

    private val _pontosGastronomicos = MutableStateFlow<List<PontoGastronomico>>(emptyList())
    val pontosGastronomicos: StateFlow<List<PontoGastronomico>> = _pontosGastronomicos

    fun getPontosGastronomicos() {
        viewModelScope.launch {
            try {
                _pontosGastronomicos.value = repository.getPontosGastronomicos()
            } catch (e: Exception) {
                _pontosGastronomicos.value = emptyList()
            }
        }
    }

    fun getPontosGastronomicosPorCategoria(categoria: String) {
        viewModelScope.launch {
            try {
                _pontosGastronomicos.value = repository.getPontosGastronomicosPorCategoria(categoria)
            } catch (e: Exception) {
                _pontosGastronomicos.value = emptyList()
            }
        }
    }
}
