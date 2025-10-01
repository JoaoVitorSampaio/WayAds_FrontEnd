package com.wayads.ui.gastronomia.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayads.data.model.Receita
import com.wayads.data.repository.ReceitaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceitaDetailViewModel @Inject constructor(
    private val repository: ReceitaRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _receita = MutableStateFlow<Receita?>(null)
    val receita: StateFlow<Receita?> = _receita

    init {
        savedStateHandle.get<String>("receitaId")?.let { receitaId ->
            getDetalhes(receitaId.toLong())
        }
    }

    private fun getDetalhes(id: Long) {
        viewModelScope.launch {
            try {
                _receita.value = repository.getReceita(id)
            } catch (e: Exception) {
                _receita.value = null
            }
        }
    }
}
