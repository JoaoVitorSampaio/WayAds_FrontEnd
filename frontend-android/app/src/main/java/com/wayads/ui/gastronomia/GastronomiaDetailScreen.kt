package com.wayads.ui.gastronomia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wayads.ui.gastronomia.viewmodel.GastronomiaDetailViewModel

@Composable
fun GastronomiaDetailScreen(
    navController: NavController,
    viewModel: GastronomiaDetailViewModel = hiltViewModel()
) {
    val pontoGastronomico by viewModel.pontoGastronomico.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a1a)),
        contentAlignment = Alignment.Center
    ) {
        pontoGastronomico?.let { ponto ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "http://10.0.2.2:8081" + ponto.imagemUrl,
                    contentDescription = ponto.nome,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(ponto.nome, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(ponto.localizacao, fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Text(ponto.descricao, fontSize = 16.sp, color = Color.LightGray)
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Voltar")
                }
            }
        } ?: run {
            CircularProgressIndicator()
        }
    }
}
