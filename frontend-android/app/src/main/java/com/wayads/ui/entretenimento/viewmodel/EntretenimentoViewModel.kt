package com.wayads.ui.entretenimento.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.data.model.Movie
import com.wayads.data.model.Noticia
import com.wayads.repository.MovieRepository
import com.wayads.repository.NoticiaRepository
import com.wayads.ui.entretenimento.EntretenimentoCategoria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Success(val movies: List<Movie>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}

sealed class EsporteUiState {
    object Loading : EsporteUiState()
    data class Success(val noticias: List<Noticia>) : EsporteUiState()
    data class Error(val message: String) : EsporteUiState()
}

@HiltViewModel
class EntretenimentoViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val noticiaRepository: NoticiaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState

    private val _esporteUiState = MutableStateFlow<EsporteUiState>(EsporteUiState.Loading)
    val esporteUiState: StateFlow<EsporteUiState> = _esporteUiState

    private val _categoriaSelecionada = MutableStateFlow(EntretenimentoCategoria.FILMES)
    val categoriaSelecionada: StateFlow<EntretenimentoCategoria> = _categoriaSelecionada

    fun onCategoriaSelecionada(categoria: EntretenimentoCategoria) {
        _categoriaSelecionada.value = categoria
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            try {
                val movies = movieRepository.getMovies()
                _uiState.value = MovieUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = MovieUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchEsportes() {
        viewModelScope.launch {
            _esporteUiState.value = EsporteUiState.Loading
            try {
                val noticias = noticiaRepository.getNoticias("ESPORTES")
                _esporteUiState.value = EsporteUiState.Success(noticias)
            } catch (e: Exception) {
                _esporteUiState.value = EsporteUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getMovieByTitle(title: String): Movie? {
        return (uiState.value as? MovieUiState.Success)?.movies?.find { it.title == title }
    }
}