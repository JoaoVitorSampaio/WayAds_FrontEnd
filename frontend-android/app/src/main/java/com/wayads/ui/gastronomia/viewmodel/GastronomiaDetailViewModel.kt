package com.wayads.ui.gastronomia.viewmodel

import androidx.lifecycle.SavedStateHandle
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
class GastronomiaDetailViewModel @Inject constructor(
    private val repository: PontoGastronomicoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pontoGastronomico = MutableStateFlow<PontoGastronomico?>(null)
    val pontoGastronomico: StateFlow<PontoGastronomico?> = _pontoGastronomico

    init {
        savedStateHandle.get<String>("pontoId")?.let { pontoId ->
            getDetalhes(pontoId.toLong())
        }
    }

    private fun getDetalhes(id: Long) {
        viewModelScope.launch {
            try {
                _pontoGastronomico.value = repository.getPontoGastronomico(id)
            } catch (e: Exception) {
                _pontoGastronomico.value = null
            }
        }
    }
}
