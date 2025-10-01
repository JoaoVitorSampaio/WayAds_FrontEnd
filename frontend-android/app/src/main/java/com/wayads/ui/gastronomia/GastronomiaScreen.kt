package com.wayads.ui.gastronomia

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wayads.app.R
import com.wayads.data.model.PontoGastronomico
import com.wayads.ui.anuncioestatico.StaticAdBanner
import com.wayads.ui.gastronomia.components.GastronomiaMenuItem
import com.wayads.ui.gastronomia.components.PontoGastronomicoCard
import com.wayads.ui.gastronomia.viewmodel.GastronomiaViewModel

@Composable
fun GastronomiaScreen(
    navController: NavController, // The main app NavController
    viewModel: GastronomiaViewModel = hiltViewModel()
) {
    val pontos by viewModel.pontosGastronomicos.collectAsState()
    var selectedCategoria by remember { mutableStateOf("TODAS") }
    var screenToShow by remember { mutableStateOf("ESTABELECIMENTOS") }
    val localNavController = rememberNavController()

    // Fetch initial data
    LaunchedEffect(Unit) {
        viewModel.getPontosGastronomicos()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        Row(
            modifier = Modifier.height(597.dp)
        ) {
            // Main content area with NavHost
            Box(modifier = Modifier.width(938.dp)) {
                 when (screenToShow) {
                    "ESTABELECIMENTOS" -> {
                        NavHost(navController = localNavController, startDestination = "ponto_list") {
                            composable("ponto_list") {
                                GastronomiaList(
                                    pontos = pontos,
                                    onItemClick = { pontoId ->
                                        localNavController.navigate("ponto_detail/$pontoId")
                                    }
                                )
                            }
                            composable("ponto_detail/{pontoId}") {
                                GastronomiaDetailScreen(navController = localNavController)
                            }
                        }
                    }
                    "RECEITAS" -> {
                        NavHost(navController = localNavController, startDestination = "receita_list") {
                            composable("receita_list") {
                                ReceitasScreen(
                                    onReceitaClick = { receitaId ->
                                        localNavController.navigate("receita_detail/$receitaId")
                                    }
                                )
                            }
                            composable("receita_detail/{receitaId}") {
                                ReceitaDetailScreen(navController = localNavController)
                            }
                        }
                    }
                }
            }

            // Side Menu
            Column(
                modifier = Modifier
                    .width(342.dp)
                    .fillMaxHeight()
                    .background(Color.Black)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                GastronomiaMenuItem(text = "Voltar", isSelected = false, onClick = { navController.popBackStack() })
                Spacer(modifier = Modifier.height(16.dp))
                GastronomiaMenuItem(
                    text = "Todas",
                    isSelected = selectedCategoria == "TODAS",
                    onClick = {
                        screenToShow = "ESTABELECIMENTOS"
                        selectedCategoria = "TODAS"
                        viewModel.getPontosGastronomicos()
                        localNavController.popBackStack(localNavController.graph.startDestinationId, false)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                 GastronomiaMenuItem(
                    text = "Receitas",
                    isSelected = screenToShow == "RECEITAS",
                    onClick = { 
                        screenToShow = "RECEITAS" 
                        localNavController.popBackStack(localNavController.graph.startDestinationId, false)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GastronomiaMenuItem(
                    text = "Oriental",
                    isSelected = selectedCategoria == "ORIENTAL",
                    onClick = {
                        screenToShow = "ESTABELECIMENTOS"
                        selectedCategoria = "ORIENTAL"
                        viewModel.getPontosGastronomicosPorCategoria("ORIENTAL")
                        localNavController.popBackStack(localNavController.graph.startDestinationId, false)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GastronomiaMenuItem(
                    text = "Regional",
                    isSelected = selectedCategoria == "REGIONAL",
                    onClick = {
                        screenToShow = "ESTABELECIMENTOS"
                        selectedCategoria = "REGIONAL"
                        viewModel.getPontosGastronomicosPorCategoria("REGIONAL")
                        localNavController.popBackStack(localNavController.graph.startDestinationId, false)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GastronomiaMenuItem(
                    text = "Massas",
                    isSelected = selectedCategoria == "MASSAS",
                    onClick = {
                        screenToShow = "ESTABELECIMENTOS"
                        selectedCategoria = "MASSAS"
                        viewModel.getPontosGastronomicosPorCategoria("MASSAS")
                        localNavController.popBackStack(localNavController.graph.startDestinationId, false)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GastronomiaMenuItem(
                    text = "Hambúrguer",
                    isSelected = selectedCategoria == "HAMBURGUER",
                    onClick = {
                        screenToShow = "ESTABELECIMENTOS"
                        selectedCategoria = "HAMBURGUER"
                        viewModel.getPontosGastronomicosPorCategoria("HAMBURGUER")
                        localNavController.popBackStack(localNavController.graph.startDestinationId, false)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GastronomiaMenuItem(
                    text = "Pizza",
                    isSelected = selectedCategoria == "PIZZA",
                    onClick = {
                        screenToShow = "ESTABELECIMENTOS"
                        selectedCategoria = "PIZZA"
                        viewModel.getPontosGastronomicosPorCategoria("PIZZA")
                        localNavController.popBackStack(localNavController.graph.startDestinationId, false)
                    }
                )
            }
        }
        // Bottom Banner
        StaticAdBanner()
    }
}

@Composable
private fun GastronomiaList(
    pontos: List<PontoGastronomico>,
    onItemClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a1a))
            .padding(16.dp)
    ) {
        Text(
            text = "Gastronomia",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pontos) { ponto ->
                PontoGastronomicoCard(ponto = ponto, onClick = {
                    onItemClick(ponto.id)
                })
            }
        }
    }
}