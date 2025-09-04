package com.wayads.mobile_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.mobile_app.data.model.Ad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Estado da UI para a tela principal
data class HomeUiState(
    val ads: List<Ad> = emptyList(),
    val isLoading: Boolean = false
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchAds()
    }

    private fun fetchAds() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Simula o carregamento de dados da rede ou banco de dados
            kotlinx.coroutines.delay(1000)
            val dummyAds = listOf(
                Ad("1", "Anúncio 1", "https://via.placeholder.com/600x200.png/0000FF/FFFFFF?text=Anuncio+1"),
                Ad("2", "Anúncio 2", "https://via.placeholder.com/600x200.png/FF0000/FFFFFF?text=Anuncio+2"),
                Ad("3", "Anúncio 3", "https://via.placeholder.com/600x200.png/00FF00/FFFFFF?text=Anuncio+3")
            )
            _uiState.value = _uiState.value.copy(ads = dummyAds, isLoading = false)
        }
    }
}
