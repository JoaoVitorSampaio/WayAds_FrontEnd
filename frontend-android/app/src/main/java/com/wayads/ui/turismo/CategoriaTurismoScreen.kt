package com.wayads.ui.turismo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wayads.ui.turismo.viewmodel.TurismoViewModel

import coil.compose.AsyncImage

@Composable
fun CategoriaTurismoScreen(categoria: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "categoria_list") {
        composable("categoria_list") {
            CategoriaList(
                navController = navController,
                categoria = categoria
            )
        }
        composable("turismo_detail/{pontoId}") {
            TurismoDetailScreen(navController = navController)
        }
    }
}

@Composable
private fun CategoriaList(
    navController: NavController,
    categoria: String,
    viewModel: TurismoViewModel = hiltViewModel()
) {
    val pontosTuristicos by viewModel.pontosTuristicos.collectAsState()

    LaunchedEffect(categoria) {
        viewModel.getPontosTuristicos(categoria)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pontosTuristicos) { ponto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clickable {
                        navController.navigate("turismo_detail/${ponto.id}")
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                ) {
                    AsyncImage(
                        model = "http://10.0.2.2:8081" + ponto.urlFotoPrincipal,
                        contentDescription = ponto.nome,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(160.dp)
                            .fillMaxHeight()
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(ponto.nome, color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}
