package com.wayads.ui.anuncioestatico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.data.model.AnuncioEstatico
import com.wayads.data.repository.AnuncioEstaticoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StaticAdUiState {
    object Loading : StaticAdUiState()
    data class Success(val ad: AnuncioEstatico) : StaticAdUiState()
    data class Error(val message: String) : StaticAdUiState()
}

@HiltViewModel
class StaticAdViewModel @Inject constructor(
    private val repository: AnuncioEstaticoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StaticAdUiState>(StaticAdUiState.Loading)
    val uiState: StateFlow<StaticAdUiState> = _uiState

    init {
        fetchStaticAd()
    }

    private fun fetchStaticAd() {
        viewModelScope.launch {
            _uiState.value = StaticAdUiState.Loading
            try {
                val ad = repository.getAnuncioEstatico()
                _uiState.value = StaticAdUiState.Success(ad)
            } catch (e: Exception) {
                _uiState.value = StaticAdUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}
