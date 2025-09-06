package com.wayads.ui.home

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.BrightnessLow
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.wayads.app.R

@Composable
fun HomeScreen(navController: NavController) {
    // Get context, window, and audio manager for system controls
    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    // State to hold the current brightness level
    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }

    // State for controlling dialog visibility
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

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black) // Background para a tela inteira
    ) {
        // Área de conteúdo principal (Anúncio + Menu)
        Row(
            modifier = Modifier.height(597.dp)
        ) {
            // Banner principal
            Image(
                painter = painterResource(id = R.drawable.subway),
                contentDescription = "Banner principal",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(938.dp)
                    .fillMaxHeight()
            )

            // Menu lateral
            Column(
                modifier = Modifier
                    .width(342.dp)
                    .fillMaxHeight()
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botões de navegação ocupam o espaço disponível
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MenuItem(navController = navController, route = "atualidades", text = "Atualidades", traceColor = Color.Magenta)
                    MenuItem(navController = navController, route = "kids", text = "Kids", traceColor = Color.Blue)
                    MenuItem(navController = navController, route = "turismo", text = "Turismo", traceColor = Color.Green)
                    MenuItem(navController = navController, route = "gastronomia", text = "Gastronomia", traceColor = Color.Red)
                    MenuItem(navController = navController, route = "entretenimento", text = "Entretenimento", traceColor = Color.Yellow)
                }

                // Ícones de controle na parte inferior
                Row(
                    modifier = Modifier.padding(bottom = 14.dp), // Espaçamento da borda inferior
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showVolumeDialog = true }, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Controle de Volume", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(14.dp)) // Espaço entre os ícones
                    IconButton(onClick = { showBrightnessDialog = true }, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.WbSunny, contentDescription = "Controle de Brilho", tint = Color.White)
                    }
                }
            }
        }

        // Banner inferior
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
fun MenuItem(navController: NavController, route: String, text: String, traceColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { navController.navigate(route) },
            modifier = Modifier
                .width(301.dp)
                .height(94.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RectangleShape // Sem cantos arredondados
        ) {
            Text(text, textAlign = TextAlign.Center, color = Color.White)
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
