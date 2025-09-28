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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.wayads.app.R
import com.wayads.ui.gastronomia.model.*
import com.wayads.ui.gastronomia.components.*

@Composable
fun GastronomiaScreen(navController: NavController) {
    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }
    var showVolumeDialog by remember { mutableStateOf(false) }
    var showBrightnessDialog by remember { mutableStateOf(false) }
    
    // Estados para filtros
    var filtroRegiao by remember { mutableStateOf(RegiaoGeografica.TODAS) }
    var filtroCategoria by remember { mutableStateOf(CategoriaComida.TODOS) }
    var anunciosFiltrados by remember { mutableStateOf(GastronomiaData.anuncios) }
    
    // Aplicar filtros em tempo real
    LaunchedEffect(filtroRegiao, filtroCategoria) {
        anunciosFiltrados = GastronomiaData.anuncios.filter { anuncio ->
            val regiaoMatch = filtroRegiao == RegiaoGeografica.TODAS || anuncio.regiao == filtroRegiao
            val categoriaMatch = filtroCategoria == CategoriaComida.TODOS || anuncio.categoria == filtroCategoria
            regiaoMatch && categoriaMatch
        }
    }

    if (showVolumeDialog) {
        ControlDialog(
            onDismissRequest = { showVolumeDialog = false },
            title = "Volume",
            onDecrease = { audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0) },
            onIncrease = { audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0) },
            decreaseIcon = Icons.Default.VolumeDown,
            increaseIcon = Icons.Default.VolumeUp
        )
    }

    if (showBrightnessDialog) {
        ControlDialog(
            onDismissRequest = { showBrightnessDialog = false },
            title = "Brilho",
            onDecrease = {
                val newBrightness = (currentBrightness - 0.1f).coerceIn(0.0f, 1.0f)
                val attributes = window.attributes
                attributes.screenBrightness = newBrightness
                window.attributes = attributes
                currentBrightness = newBrightness
            },
            onIncrease = {
                val newBrightness = (currentBrightness + 0.1f).coerceIn(0.0f, 1.0f)
                val attributes = window.attributes
                attributes.screenBrightness = newBrightness
                window.attributes = attributes
                currentBrightness = newBrightness
            },
            decreaseIcon = Icons.Default.BrightnessLow,
            increaseIcon = Icons.Default.BrightnessHigh
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        // Container principal com altura fixa
        Row(
            modifier = Modifier.height(597.dp)
        ) {
            // Área principal com listagem de estabelecimentos
            Column(
                modifier = Modifier
                    .width(938.dp)
                    .fillMaxHeight()
                    .background(Color(0xFF1a1a1a))
                    .padding(16.dp)
            ) {
                // Título da seção
                Text(
                    text = "Gastronomia",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Filtros
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Filtro por Região
                    FilterDropdown(
                        label = "Região: ${filtroRegiao.displayName}",
                        options = RegiaoGeografica.values().toList(),
                        selectedOption = filtroRegiao,
                        onOptionSelected = { filtroRegiao = it },
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Filtro por Categoria
                    FilterDropdown(
                        label = "Categoria: ${filtroCategoria.displayName}",
                        options = CategoriaComida.values().toList(),
                        selectedOption = filtroCategoria,
                        onOptionSelected = { filtroCategoria = it },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Lista: se for Receitas, exibir receitas; senão, exibir anúncios
                if (filtroRegiao == RegiaoGeografica.RECEITAS) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(GastronomiaData.receitas) { receita ->
                            ReceitaCard(
                                receita = receita,
                                onClick = { navController.navigate("receita/${receita.id}") }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(anunciosFiltrados) { anuncio ->
                            AnuncioCard(anuncio = anuncio)
                        }
                    }
                }
            }

            // Menu lateral
            Column(
                modifier = Modifier
                    .width(342.dp)
                    .fillMaxHeight()
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    GastronomiaMenuItem(
                        text = "Voltar",
                        isSelected = false,
                        onClick = { navController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    GastronomiaMenuItem(
                        text = "Todas",
                        isSelected = filtroRegiao == RegiaoGeografica.TODAS,
                        onClick = { filtroRegiao = RegiaoGeografica.TODAS }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    GastronomiaMenuItem(
                        text = "Receitas",
                        isSelected = filtroRegiao == RegiaoGeografica.RECEITAS,
                        onClick = { filtroRegiao = RegiaoGeografica.RECEITAS }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    GastronomiaMenuItem(
                        text = "Oriental",
                        isSelected = filtroRegiao == RegiaoGeografica.ORIENTAL,
                        onClick = { filtroRegiao = RegiaoGeografica.ORIENTAL }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    GastronomiaMenuItem(
                        text = "Regional",
                        isSelected = filtroRegiao == RegiaoGeografica.REGIONAL,
                        onClick = { filtroRegiao = RegiaoGeografica.REGIONAL }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    GastronomiaMenuItem(
                        text = "Massas",
                        isSelected = filtroRegiao == RegiaoGeografica.MASSAS,
                        onClick = { filtroRegiao = RegiaoGeografica.MASSAS }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(
                    modifier = Modifier.padding(bottom = 14.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showVolumeDialog = true }, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Controle de Volume", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    IconButton(onClick = { showBrightnessDialog = true }, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.WbSunny, contentDescription = "Controle de Brilho", tint = Color.White)
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.anuncio_generico),
            contentDescription = "Banner inferior",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(177.dp)
        )
    }
}