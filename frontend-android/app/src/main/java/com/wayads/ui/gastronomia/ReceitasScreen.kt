package com.wayads.ui.gastronomia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wayads.ui.gastronomia.components.ReceitaCard
import com.wayads.ui.gastronomia.viewmodel.ReceitaViewModel

@Composable
fun ReceitasScreen(
    onReceitaClick: (Long) -> Unit,
    viewModel: ReceitaViewModel = hiltViewModel()
) {
    val receitas by viewModel.receitas.collectAsState()

    LazyColumn(
        modifier = Modifier.background(Color(0xFF1a1a1a)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(receitas) { receita ->
            ReceitaCard(
                receita = receita,
                onClick = { onReceitaClick(receita.id) }
            )
        }
    }
}
