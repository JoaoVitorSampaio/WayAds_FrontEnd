package com.wayads.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.data.model.Anuncio
import com.wayads.repository.AdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val ads: List<Anuncio>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val adRepository: AdRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _currentAdIndex = MutableStateFlow(0)
    val currentAdIndex: StateFlow<Int> = _currentAdIndex.asStateFlow()

    init {
        fetchAds()
    }

    private fun fetchAds() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val ads = adRepository.getAnuncios()
                _uiState.value = HomeUiState.Success(ads)
                if (ads.isNotEmpty()) {
                    startAdRotation(ads.size)
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun startAdRotation(adCount: Int) {
        viewModelScope.launch {
            while (true) {
                delay(15000)
                _currentAdIndex.value = (_currentAdIndex.value + 1) % adCount
            }
        }
    }
}
