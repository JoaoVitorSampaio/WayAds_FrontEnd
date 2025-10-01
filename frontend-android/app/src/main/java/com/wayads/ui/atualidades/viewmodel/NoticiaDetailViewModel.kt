package com.wayads.ui.atualidades.viewmodel

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

sealed class NoticiaDetailUiState {
    object Loading : NoticiaDetailUiState()
    data class Success(val noticia: Noticia) : NoticiaDetailUiState()
    data class Error(val message: String) : NoticiaDetailUiState()
}

@HiltViewModel
class NoticiaDetailViewModel @Inject constructor(
    private val noticiaRepository: NoticiaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<NoticiaDetailUiState>(NoticiaDetailUiState.Loading)
    val uiState: StateFlow<NoticiaDetailUiState> = _uiState.asStateFlow()

    private val noticiaId: Int = checkNotNull(savedStateHandle["noticiaId"])

    init {
        fetchNoticia(noticiaId)
    }

    private fun fetchNoticia(id: Int) {
        viewModelScope.launch {
            _uiState.value = NoticiaDetailUiState.Loading
            try {
                val noticia = noticiaRepository.getNoticia(id)
                _uiState.value = NoticiaDetailUiState.Success(noticia)
            } catch (e: Exception) {
                _uiState.value = NoticiaDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
