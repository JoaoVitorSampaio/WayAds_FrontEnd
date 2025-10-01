package com.wayads.ui.atualidades

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wayads.ui.anuncioestatico.StaticAdBanner
import com.wayads.ui.components.ErrorScreen
import com.wayads.ui.atualidades.viewmodel.AtualidadesUiState
import com.wayads.ui.atualidades.viewmodel.AtualidadesViewModel

@Composable
fun AtualidadesScreen(navController: NavController, viewModel: AtualidadesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }
    var showVolumeDialog by remember { mutableStateOf(false) }
    var showBrightnessDialog by remember { mutableStateOf(false) }

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

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Row(modifier = Modifier.height(597.dp)) {
            // Main content
            Box(
                modifier = Modifier
                    .width(938.dp)
                    .fillMaxHeight()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                when (val state = uiState) {
                    is AtualidadesUiState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is AtualidadesUiState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.noticias) { noticia ->
                                Card(
                                    modifier = Modifier.clickable { navController.navigate("noticiaDetail/${noticia.id}") },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.Gray)
                                ) {
                                    Column {
                                        AsyncImage(
                                            model = noticia.fotoUrl.replace("localhost", "192.168.0.3"),
                                            contentDescription = noticia.titulo,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .height(180.dp)
                                                .fillMaxWidth()
                                        )
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text(text = noticia.titulo, fontSize = 18.sp, maxLines = 2, overflow = TextOverflow.Ellipsis, color = Color.White)
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(text = noticia.descricao, fontSize = 14.sp, maxLines = 3, overflow = TextOverflow.Ellipsis, color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is AtualidadesUiState.Error -> {
                        ErrorScreen()
                    }
                }
            }

            // Side menu
            Column(
                modifier = Modifier
                    .width(342.dp)
                    .fillMaxHeight()
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AtualidadesMenuItem(text = "Voltar", isSelected = false, onClick = { navController.popBackStack() })
                    AtualidadesMenuItem(text = "Política", isSelected = selectedCategory == "POLITICA", onClick = { viewModel.fetchNoticias("POLITICA") })
                    AtualidadesMenuItem(text = "Economia", isSelected = selectedCategory == "ECONOMIA", onClick = { viewModel.fetchNoticias("ECONOMIA") })
                    AtualidadesMenuItem(text = "Mundo", isSelected = selectedCategory == "MUNDO", onClick = { viewModel.fetchNoticias("MUNDO") })
                    AtualidadesMenuItem(text = "Tecnologia", isSelected = selectedCategory == "TECNOLOGIA", onClick = { viewModel.fetchNoticias("TECNOLOGIA") })
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

        StaticAdBanner()
    }
}

@Composable
fun AtualidadesMenuItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFF2E7D32) else Color(0xFF1B5E20)

    Button(
        onClick = onClick,
        modifier = Modifier
            .width(301.dp)
            .height(94.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun ControlDialog(
    onDismissRequest: () -> Unit,
    title: String,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    decreaseIcon: ImageVector,
    increaseIcon: ImageVector
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.DarkGray,
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDecrease, modifier = Modifier.size(64.dp)) {
                        Icon(decreaseIcon, contentDescription = "Diminuir", modifier = Modifier.fillMaxSize())
                    }
                    IconButton(onClick = onIncrease, modifier = Modifier.size(64.dp)) {
                        Icon(increaseIcon, contentDescription = "Aumentar", modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}