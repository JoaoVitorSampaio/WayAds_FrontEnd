package com.wayads.ui.entretenimento.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.data.model.Movie
import com.wayads.repository.MovieRepository
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

@HiltViewModel
class EntretenimentoViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState

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

    fun getMovieByTitle(title: String): Movie? {
        return (uiState.value as? MovieUiState.Success)?.movies?.find { it.title == title }
    }
}