
package com.wayads.ui.atualidades

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.BrightnessLow
import androidx.compose.material.icons.filled.BrightnessHigh
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.wayads.app.R

/**
 * Categorias de notícias
 */
enum class Categoria(val label: String, val imagens: List<Int>) {
    POLITICA("Política", listOf(R.drawable.politica, R.drawable.politica2)),
    REGIONAL("Regional", listOf(R.drawable.regional, R.drawable.regional2)),
    POLICIAL("Policial", listOf(R.drawable.policia, R.drawable.policia2)),
    FAMOSOS("Famosos", listOf(R.drawable.famosos, R.drawable.famosos1))
}

@Composable
fun AtualidadesScreen(navController: NavController) {
    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }
    var showVolumeDialog by remember { mutableStateOf(false) }
    var showBrightnessDialog by remember { mutableStateOf(false) }

    // Categoria inicial
    var categoriaSelecionada by remember { mutableStateOf(Categoria.POLITICA) }

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
        Row(
            modifier = Modifier.height(597.dp)
        ) {
            // Área de notícias (imagens da categoria selecionada, exceto Voltar)
            Column(
                modifier = Modifier
                    .width(938.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .background(Color.Black),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                categoriaSelecionada.imagens.forEach { img ->
                    Image(
                        painter = painterResource(id = img),
                        contentDescription = "Notícia ${categoriaSelecionada.label}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Menu lateral direito
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
                    // Voltar com estado visual
                    MenuItemBlue(
                        onClick = { navController.popBackStack() },
                        text = "Voltar",
                        selected = false
                    )

                    // Botões de categorias
                    MenuItemBlue(
                        onClick = { categoriaSelecionada = Categoria.POLITICA },
                        text = "Política",
                        selected = (categoriaSelecionada == Categoria.POLITICA)
                    )
                    MenuItemBlue(
                        onClick = { categoriaSelecionada = Categoria.REGIONAL },
                        text = "Regional",
                        selected = (categoriaSelecionada == Categoria.REGIONAL)
                    )
                    MenuItemBlue(
                        onClick = { categoriaSelecionada = Categoria.POLICIAL },
                        text = "Policial",
                        selected = (categoriaSelecionada == Categoria.POLICIAL)
                    )
                    MenuItemBlue(
                        onClick = { categoriaSelecionada = Categoria.FAMOSOS },
                        text = "Famosos",
                        selected = (categoriaSelecionada == Categoria.FAMOSOS)
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
fun MenuItemBlue(
    onClick: () -> Unit,
    text: String,
    selected: Boolean
) {
    val backgroundColor = if (selected) Color(0xFF1565C0) else Color(0xFF0000AA)
    // azul mais claro quando selecionado

    Row(verticalAlignment = Alignment.CenterVertically) {
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
                .background(Color.White)
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
