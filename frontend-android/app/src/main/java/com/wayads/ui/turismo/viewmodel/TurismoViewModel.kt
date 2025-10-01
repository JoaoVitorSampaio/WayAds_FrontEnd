package com.wayads.ui.turismo.viewmodel

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
class TurismoViewModel @Inject constructor(
    private val turismoRepository: TurismoRepository
) : ViewModel() {

    private val _pontosTuristicos = MutableStateFlow<List<PontoTuristico>>(emptyList())
    val pontosTuristicos: StateFlow<List<PontoTuristico>> = _pontosTuristicos

    fun getPontosTuristicos(categoria: String) {
        viewModelScope.launch {
            try {
                _pontosTuristicos.value = turismoRepository.getPontosTuristicosPorCategoria(categoria)
            } catch (e: Exception) {
                // Handle error - for now, just emit an empty list
                _pontosTuristicos.value = emptyList()
            }
        }
    }
}
