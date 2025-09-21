package com.wayads.ui.atualidades.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.data.model.Noticia
import com.wayads.repository.NoticiaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AtualidadesUiState {
    object Loading : AtualidadesUiState()
    data class Success(val noticias: List<Noticia>) : AtualidadesUiState()
    data class Error(val message: String) : AtualidadesUiState()
}

@HiltViewModel
class AtualidadesViewModel @Inject constructor(private val noticiaRepository: NoticiaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<AtualidadesUiState>(AtualidadesUiState.Loading)
    val uiState: StateFlow<AtualidadesUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow("POLITICA")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    init {
        fetchNoticias(_selectedCategory.value)
    }

    fun fetchNoticias(category: String) {
        _selectedCategory.value = category
        viewModelScope.launch {
            _uiState.value = AtualidadesUiState.Loading
            try {
                val noticias = noticiaRepository.getNoticias(category)
                _uiState.value = AtualidadesUiState.Success(noticias)
            } catch (e: Exception) {
                _uiState.value = AtualidadesUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getNoticiaById(id: Int): Noticia? {
        return (uiState.value as? AtualidadesUiState.Success)?.noticias?.find { it.id == id }
    }
}