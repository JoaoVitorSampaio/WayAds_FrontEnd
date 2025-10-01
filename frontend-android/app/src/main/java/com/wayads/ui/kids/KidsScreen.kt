package com.wayads.ui.kids

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
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayads.app.BuildConfig

import com.wayads.app.R
import com.wayads.app.ui.kids.components.VideoPlayer
import com.wayads.app.ui.kids.viewmodel.KidsViewModel
import com.wayads.ui.anuncioestatico.StaticAdBanner

/**
 * Tela principal da seção infantil.
 */
@Composable
fun KidsScreen(
    navController: NavController,
    viewModel: KidsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }
    var showVolumeDialog by remember { mutableStateOf(false) }
    var showBrightnessDialog by remember { mutableStateOf(false) }

    var selectedButton by remember { mutableStateOf("") }
    val kidVideo by viewModel.kidVideo.collectAsState()

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
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier.height(597.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(938.dp)
                    .fillMaxHeight()
            ) {
                if (kidVideo != null) {
                    VideoPlayer(videoUrl = BuildConfig.MEDIA_BASE_URL + kidVideo!!.videoUrl)
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.kids_placeholder),
                        contentDescription = "Banner principal",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

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
                    KidsMenuItem(
                        text = "Voltar",
                        traceColor = Color.White,
                        isSelected = selectedButton == "Voltar",
                        onClick = {
                            selectedButton = "Voltar"
                            navController.popBackStack()
                        }
                    )
                    KidsMenuItem(
                        text = "Galinha Pintadinha",
                        traceColor = Color.White,
                        isSelected = selectedButton == "Galinha Pintadinha",
                        onClick = {
                            selectedButton = "Galinha Pintadinha"
                            viewModel.getKidVideo("galinha")
                        }
                    )
                    KidsMenuItem(
                        text = "Mundo Bita",
                        traceColor = Color.White,
                        isSelected = selectedButton == "Mundo Bita",
                        onClick = {
                            selectedButton = "Mundo Bita"
                            viewModel.getKidVideo("bita")
                        }
                    )
                    KidsMenuItem(
                        text = "Patati e Patatá",
                        traceColor = Color.White,
                        isSelected = selectedButton == "Patati e Patatá",
                        onClick = {
                            selectedButton = "Patati e Patatá"
                            viewModel.getKidVideo("patati")
                        }
                    )
                    KidsMenuItem(
                        text = "Patrulha Canina",
                        traceColor = Color.White,
                        isSelected = selectedButton == "Patrulha Canina",
                        onClick = {
                            selectedButton = "Patrulha Canina"
                            viewModel.getKidVideo("patrulha")
                        }
                    )
                }

                Row(
                    modifier = Modifier.padding(bottom = 14.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { showVolumeDialog = true },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            Icons.Default.VolumeUp,
                            contentDescription = "Controle de Volume",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    IconButton(
                        onClick = { showBrightnessDialog = true },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            Icons.Default.WbSunny,
                            contentDescription = "Controle de Brilho",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        StaticAdBanner()
    }
}

/**
 * Item do menu da seção infantil.
 */
@Composable
fun KidsMenuItem(text: String, traceColor: Color, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFFA70ADC) else Color(0xFF4C0065)

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

/**
 * Dialog para controle de volume e brilho.
 */
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
                    IconButton(
                        onClick = onDecrease,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            decreaseIcon,
                            contentDescription = "Diminuir",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(
                        onClick = onIncrease,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            increaseIcon,
                            contentDescription = "Aumentar",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}