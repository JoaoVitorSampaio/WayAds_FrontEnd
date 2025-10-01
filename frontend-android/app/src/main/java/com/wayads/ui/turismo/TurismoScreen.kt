package com.wayads.ui.turismo

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wayads.ui.anuncioestatico.StaticAdBanner
import com.wayads.app.R

// ---------- MODELO ----------
data class Praia(
    val nome: String,
    val imagem: Int,
    val descricao: String
)

// ---------- TELA PRINCIPAL ----------
@Composable
fun TurismoScreen(navController: NavController) {
    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }
    var showVolumeDialog by remember { mutableStateOf(false) }
    var showBrightnessDialog by remember { mutableStateOf(false) }

    var selectedButton by remember { mutableStateOf("Praias") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            // Conteúdo principal
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                when (selectedButton) {
                    "Praias" -> {
                        val innerNavController = rememberNavController()
                        NavHost(
                            navController = innerNavController,
                            startDestination = "listaPraias",
                            modifier = Modifier.fillMaxSize()
                        ) {
                            composable("listaPraias") { ListaDePraias(innerNavController) }
                            composable("detalhePraia/{praiaNome}") { backStackEntry ->
                                val praiaNome = backStackEntry.arguments?.getString("praiaNome") ?: ""
                                DetalhePraiaScreen(praiaNome, innerNavController)
                            }
                        }
                    }
                    "Histórico" -> FullImage(R.drawable.historico_bg, "Histórico")
                    "Eventos" -> FullImage(R.drawable.eventos_bg, "Eventos")
                    "Aventura" -> FullImage(R.drawable.aventura_bg, "Aventura")
                }
            }

            // 👉 CORREÇÃO 1: A LARGURA DA COLUNA DO MENU FOI AJUSTADA
            Column(
                modifier = Modifier
                    .width(342.dp) // Ajustado para corresponder ao layout antigo
                    .fillMaxHeight()
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
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
                        onClick = { selectedButton = "Praias" }
                    )
                    TurismoMenuItem(
                        text = "Histórico",
                        isSelected = selectedButton == "Histórico",
                        onClick = { selectedButton = "Histórico" }
                    )
                    TurismoMenuItem(
                        text = "Eventos",
                        isSelected = selectedButton == "Eventos",
                        onClick = { selectedButton = "Eventos" }
                    )
                    TurismoMenuItem(
                        text = "Aventura",
                        isSelected = selectedButton == "Aventura",
                        onClick = { selectedButton = "Aventura" }
                    )
                }

                // Ícones de controle
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
        StaticAdBanner()
    }

    // Dialogs
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
}

// ---------- COMPONENTE IMAGEM CHEIA ----------
@Composable
fun FullImage(resId: Int, description: String) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

// ---------- LISTA DE PRAIAS ----------
@Composable
fun ListaDePraias(navController: NavController) {
    val praias = listOf(
        Praia("Praia do Bessa", R.drawable.bessa, "Praia tranquila com ótima faixa de areia."),
        Praia("Praia de Camboinha", R.drawable.camboinha, "Famosa pelos passeios às piscinas naturais."),
        Praia("Barra de Gramame", R.drawable.gramame, "Encontro do rio com o mar, muito procurada para passeios."),
        Praia("Praia de Intermares", R.drawable.intermares, "Conhecida pelo surfe e pelos ninhos de tartarugas."),
        Praia("Praia de Tambaú", R.drawable.tambau, "Cartão-postal de João Pessoa, cheia de vida e bares.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(praias) { praia ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clickable { navController.navigate("detalhePraia/${praia.nome}") },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = praia.imagem),
                        contentDescription = praia.nome,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(160.dp)
                            .fillMaxHeight()
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(praia.nome, color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}

// ---------- DETALHE DA PRAIA ----------
@Composable
fun DetalhePraiaScreen(praiaNome: String, navController: NavController) {
    val praias = listOf(
        Praia("Praia do Bessa", R.drawable.bessa, "Praia tranquila com ótima faixa de areia."),
        Praia("Praia de Camboinha", R.drawable.camboinha, "Famosa pelos passeios às piscinas naturais."),
        Praia("Barra de Gramame", R.drawable.gramame, "Encontro do rio com o mar, muito procurada para passeios."),
        Praia("Praia de Intermares", R.drawable.intermares, "Conhecida pelo surfe e pelos ninhos de tartarugas."),
        Praia("Praia de Tambaú", R.drawable.tambau, "Cartão-postal de João Pessoa, cheia de vida e bares.")
    )

    val praia = praias.find { it.nome == praiaNome }

    praia?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = it.imagem),
                contentDescription = it.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(it.nome, fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(it.descricao, fontSize = 16.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Voltar")
            }
        }
    }
}

// ---------- MENU ITEM ----------
// 👉 CORREÇÃO 2: ESTE COMPONENTE FOI TOTALMENTE AJUSTADO PARA FICAR IGUAL AO ANTIGO
@Composable
fun TurismoMenuItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected && text == "Voltar" -> Color(0xFF00E676)
        isSelected -> Color(0xFF2E7D32)
        else -> Color(0xFF1B5E20)
    }
    val traceColor = if (text == "Voltar" && isSelected) backgroundColor else Color.White

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(301.dp)  // Largura do botão antigo
                .height(94.dp), // Altura do botão antigo
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            shape = RectangleShape
        ) {
            // Box para forçar o alinhamento à esquerda
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text,
                    modifier = Modifier.padding(start = 16.dp), // Padding interno do texto
                    color = Color.White,
                    fontSize = 24.sp // Tamanho da fonte antigo
                )
            }
        }
        // Barra lateral
        Box(
            modifier = Modifier
                .width(10.dp)   // Largura da barra antiga
                .height(94.dp)  // Altura da barra antiga
                .background(traceColor)
        )
    }
}

// ---------- DIALOG ----------
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