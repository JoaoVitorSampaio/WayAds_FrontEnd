package com.wayads.ui.atualidades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wayads.ui.atualidades.viewmodel.NoticiaDetailUiState
import com.wayads.ui.atualidades.viewmodel.NoticiaDetailViewModel
import com.wayads.ui.components.QrCodeImage

@Composable
fun NoticiaDetailScreen(navController: NavController, viewModel: NoticiaDetailViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is NoticiaDetailUiState.Loading -> {
                CircularProgressIndicator()
            }
            is NoticiaDetailUiState.Success -> {
                val noticia = state.noticia
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = noticia.titulo, fontSize = 24.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = noticia.fotoUrl.replace("localhost", "192.168.0.2"),
                        contentDescription = noticia.titulo,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = noticia.descricao, fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    QrCodeImage(content = noticia.linkQr, size = 256)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text(text = "Voltar")
                    }
                }
            }
            is NoticiaDetailUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.message, fontSize = 24.sp, color = Color.Red)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text(text = "Voltar")
                    }
                }
            }
        }
    }
}