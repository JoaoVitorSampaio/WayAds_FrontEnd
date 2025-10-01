package com.wayads.ui.entretenimento.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.data.model.Noticia
import com.wayads.data.repository.NoticiaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EsporteDetailUiState {
    object Loading : EsporteDetailUiState()
    data class Success(val noticia: Noticia) : EsporteDetailUiState()
    data class Error(val message: String) : EsporteDetailUiState()
}

@HiltViewModel
class EsporteDetailViewModel @Inject constructor(
    private val noticiaRepository: NoticiaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<EsporteDetailUiState>(EsporteDetailUiState.Loading)
    val uiState: StateFlow<EsporteDetailUiState> = _uiState.asStateFlow()

    private val noticiaId: Int = checkNotNull(savedStateHandle["esporteId"])

    init {
        fetchNoticia(noticiaId)
    }

    private fun fetchNoticia(id: Int) {
        viewModelScope.launch {
            _uiState.value = EsporteDetailUiState.Loading
            try {
                val noticia = noticiaRepository.getNoticia(id)
                _uiState.value = EsporteDetailUiState.Success(noticia)
            } catch (e: Exception) {
                _uiState.value = EsporteDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
