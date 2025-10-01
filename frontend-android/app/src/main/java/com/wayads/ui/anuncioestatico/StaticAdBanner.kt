package com.wayads.ui.anuncioestatico

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.wayads.ui.components.ErrorScreen
import com.wayads.ui.anuncioestatico.viewmodel.StaticAdUiState
import com.wayads.ui.anuncioestatico.viewmodel.StaticAdViewModel

@Composable
fun StaticAdBanner(modifier: Modifier = Modifier) {
    val viewModel: StaticAdViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(177.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is StaticAdUiState.Loading -> {
                CircularProgressIndicator()
            }
            is StaticAdUiState.Success -> {
                AsyncImage(
                    model = state.ad.imagemUrl.replace("localhost", "192.168.0.3"),
                    contentDescription = state.ad.titulo,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is StaticAdUiState.Error -> {
                ErrorScreen(message = "Não foi possível carregar o anúncio.")
            }
        }
    }
}
