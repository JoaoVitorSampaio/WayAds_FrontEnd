package com.wayads.app.ui.kids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.app.data.model.kids.Kids
import com.wayads.app.data.repository.kids.KidsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KidsViewModel @Inject constructor(
    private val kidsRepository: KidsRepository
) : ViewModel() {

    private val _kidVideo = MutableStateFlow<Kids?>(null)
    val kidVideo: StateFlow<Kids?> = _kidVideo

    fun getKidVideo(nome: String) {
        viewModelScope.launch {
            try {
                _kidVideo.value = kidsRepository.getKidVideo(nome)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}