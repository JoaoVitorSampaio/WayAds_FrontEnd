package com.wayads.ui.turismo

import com.wayads.app.BuildConfig
import coil.compose.AsyncImage
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayads.ui.turismo.viewmodel.TurismoDetailViewModel

@Composable
fun TurismoDetailScreen(
    navController: NavController,
    viewModel: TurismoDetailViewModel = hiltViewModel()
) {
    val pontoTuristico by viewModel.pontoTuristico.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        pontoTuristico?.let { ponto ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = BuildConfig.MEDIA_BASE_URL + ponto.urlFotoPrincipal,
                    contentDescription = ponto.nome,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(ponto.nome, fontSize = 24.sp, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(ponto.descricao, fontSize = 16.sp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Voltar")
                }
            }
        } ?: run {
            // Show a loading indicator or an error message
            CircularProgressIndicator()
        }
    }
}
