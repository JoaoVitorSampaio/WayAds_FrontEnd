package com.wayads.ui.gastronomia.viewmodel

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
class ReceitaViewModel @Inject constructor(
    private val repository: ReceitaRepository
) : ViewModel() {

    private val _receitas = MutableStateFlow<List<Receita>>(emptyList())
    val receitas: StateFlow<List<Receita>> = _receitas

    init {
        getReceitas()
    }

    fun getReceitas() {
        viewModelScope.launch {
            try {
                _receitas.value = repository.getReceitas()
            } catch (e: Exception) {
                _receitas.value = emptyList()
            }
        }
    }
}
