package com.wayads.ui.turismo

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.wayads.app.R

@Composable
fun TurismoScreen(navController: NavController) {
    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }
    var showVolumeDialog by remember { mutableStateOf(false) }
    var showBrightnessDialog by remember { mutableStateOf(false) }

    // Inicia com um botão e imagem padrão
    var selectedButton by remember { mutableStateOf("Praias") }
    var currentImage by remember { mutableStateOf(R.drawable.praias_bg) }

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
        // Container principal com ALTURA FIXA para evitar que os botões estiquem
        Row(
            modifier = Modifier.height(597.dp)
        ) {
            Image(
                painter = painterResource(id = currentImage),
                contentDescription = "Banner principal",
                contentScale = ContentScale.FillBounds, // Preenche sem cortar (evita "zoom" da imagem)
                modifier = Modifier
                    .width(938.dp)
                    .fillMaxHeight()
            )

            // Coluna do menu com LARGURA FIXA para evitar que a faixa preta estique
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
                    TurismoMenuItem(
                        text = "Voltar",
                        isSelected = selectedButton == "Voltar",
                        onClick = {
                            selectedButton = "Voltar"
                            navController.popBackStack()
                        }
                    )
                    TurismoMenuItem(
                        text = "Praias",
                        isSelected = selectedButton == "Praias",
                        onClick = {
                            currentImage = R.drawable.praias_bg
                            selectedButton = "Praias"
                        }
                    )
                    TurismoMenuItem(
                        text = "Histórico",
                        isSelected = selectedButton == "Histórico",
                        onClick = {
                            currentImage = R.drawable.historico_bg
                            selectedButton = "Histórico"
                        }
                    )
                    TurismoMenuItem(
                        text = "Eventos",
                        isSelected = selectedButton == "Eventos",
                        onClick = {
                            currentImage = R.drawable.eventos_bg
                            selectedButton = "Eventos"
                        }
                    )
                    TurismoMenuItem(
                        text = "Aventura",
                        isSelected = selectedButton == "Aventura",
                        onClick = {
                            currentImage = R.drawable.aventura_bg
                            selectedButton = "Aventura"
                        }
                    )
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

@Composable
fun TurismoMenuItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected && text == "Voltar" -> Color(0xFF00E676)   // Cor para "Voltar" quando selecionado
        isSelected -> Color(0xFF2E7D32)                       // Cor para outros botões quando selecionados
        else -> Color(0xFF1B5E20)                             // Cor para QUALQUER botão quando não está selecionado
    }

    val traceColor = if (text == "Voltar" && isSelected) backgroundColor else Color.White

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
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
        Box(
            modifier = Modifier
                .width(10.dp)
                .height(94.dp)
                .background(traceColor)
        )
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