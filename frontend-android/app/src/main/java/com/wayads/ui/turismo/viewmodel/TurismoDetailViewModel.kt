package com.wayads.ui.turismo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.app.data.model.turismo.PontoTuristico
import com.wayads.data.repository.turismo.TurismoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TurismoDetailViewModel @Inject constructor(
    private val turismoRepository: TurismoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pontoTuristico = MutableStateFlow<PontoTuristico?>(null)
    val pontoTuristico: StateFlow<PontoTuristico?> = _pontoTuristico

    init {
        savedStateHandle.get<String>("pontoId")?.let { pontoId ->
            getDetalhes(pontoId.toLong())
        }
    }

    private fun getDetalhes(id: Long) {
        viewModelScope.launch {
            try {
                _pontoTuristico.value = turismoRepository.getPontoTuristico(id)
            } catch (e: Exception) {
                // Handle error
                _pontoTuristico.value = null
            }
        }
    }
}
