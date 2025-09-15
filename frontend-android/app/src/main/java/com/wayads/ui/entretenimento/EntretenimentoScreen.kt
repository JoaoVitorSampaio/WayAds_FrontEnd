package com.wayads.ui.entretenimento

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.BrightnessLow
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wayads.app.R

data class Filme(
    val nome: String,
    val imagem: Int,
    val descricao: String
)

enum class EntretenimentoCategoria(val label: String) {
    FILMES("Filmes em cartaz"),
    MUSICAS("Músicas"),
    ESPORTES("Esportes"),
    EVENTOS("Eventos & Cultura")
}

@Composable
fun EntretenimentoScreen(navController: NavController) {
    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }
    var showVolumeDialog by remember { mutableStateOf(false) }
    var showBrightnessDialog by remember { mutableStateOf(false) }

    var categoriaSelecionada by remember { mutableStateOf(EntretenimentoCategoria.FILMES) }

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
            Column(
                modifier = Modifier
                    .width(938.dp)
                    .fillMaxHeight()
                    .background(Color.Black),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (categoriaSelecionada) {
                    EntretenimentoCategoria.FILMES -> {
                        val innerNavController = rememberNavController()
                        NavHost(
                            navController = innerNavController,
                            startDestination = "listaFilmes",
                            modifier = Modifier.fillMaxSize()
                        ) {
                            composable("listaFilmes") { ListaDeFilmes(innerNavController) }
                            composable("detalheFilme/{filmeNome}") { backStackEntry ->
                                val filmeNome = backStackEntry.arguments?.getString("filmeNome") ?: ""
                                DetalheFilmeScreen(filmeNome, innerNavController)
                            }
                        }
                    }
                    else -> {
                        val imagens = when (categoriaSelecionada) {
                            EntretenimentoCategoria.MUSICAS -> listOf(R.drawable.anuncio_generico, R.drawable.subway)
                            EntretenimentoCategoria.ESPORTES -> listOf(R.drawable.anuncio_generico, R.drawable.subway)
                            EntretenimentoCategoria.EVENTOS -> listOf(R.drawable.eventos_bg)
                            else -> emptyList()
                        }
                        Column(
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            imagens.forEach { img ->
                                Image(
                                    painter = painterResource(id = img),
                                    contentDescription = "Imagem da categoria ${categoriaSelecionada.label}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(280.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
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
                    MenuItemEntretenimento(
                        onClick = { navController.popBackStack() },
                        text = "Voltar",
                        selected = false
                    )

                    MenuItemEntretenimento(
                        onClick = { categoriaSelecionada = EntretenimentoCategoria.FILMES },
                        text = "Filmes em cartaz",
                        selected = (categoriaSelecionada == EntretenimentoCategoria.FILMES)
                    )
                    MenuItemEntretenimento(
                        onClick = { categoriaSelecionada = EntretenimentoCategoria.MUSICAS },
                        text = "Músicas",
                        selected = (categoriaSelecionada == EntretenimentoCategoria.MUSICAS)
                    )
                    MenuItemEntretenimento(
                        onClick = { categoriaSelecionada = EntretenimentoCategoria.ESPORTES },
                        text = "Esportes",
                        selected = (categoriaSelecionada == EntretenimentoCategoria.ESPORTES)
                    )
                    MenuItemEntretenimento(
                        onClick = { categoriaSelecionada = EntretenimentoCategoria.EVENTOS },
                        text = "Eventos & Cultura",
                        selected = (categoriaSelecionada == EntretenimentoCategoria.EVENTOS)
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
fun ListaDeFilmes(navController: NavController) {
    val filmes = listOf(
        Filme("Invocação do Mal: O Último Ritual", R.drawable.invoc4, "Ed e Lorraine Warren investigam a assombração da família Smurl na Pensilvânia, um de seus casos mais famosos, enquanto se aproximam da aposentadoria."),
        Filme("Demon Slayer: Kimetsu no Yaiba – O Filme: Mugen Train", R.drawable.demonslayer, "Tanjiro e seus amigos se juntam ao Hashira das Chamas, Kyojuro Rengoku, para investigar o desaparecimento de pessoas em um trem misterioso."),
        Filme("A Longa Marcha: Caminhe ou Morra", R.drawable.longamacha, "Em um futuro distópico, jovens competem em uma caminhada brutal sem parar, onde o último sobrevivente ganha um grande prêmio."),
        Filme("A Vida de Chuck", R.drawable.vidachuck, "A história da vida de Charles 'Chuck' Krantz contada em ordem cronológica inversa, começando com sua morte e retrocedendo até sua infância."),
        Filme("O Pior Homem de Londres", R.drawable.piorhomemlondres, "A história de Charles Augustus Howell, um negociante de arte e mestre da chantagem na Londres vitoriana, que inspirou o criador de Sherlock Holmes.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(filmes) { filme ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clickable { navController.navigate("detalheFilme/${filme.nome}") },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = filme.imagem),
                        contentDescription = filme.nome,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(160.dp)
                            .fillMaxHeight()
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(filme.nome, color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun DetalheFilmeScreen(filmeNome: String, navController: NavController) {
    val filmes = listOf(
        Filme("Invocação do Mal: O Último Ritual", R.drawable.invoc4, "Ed e Lorraine Warren investigam a assombração da família Smurl na Pensilvânia, um de seus casos mais famosos, enquanto se aproximam da aposentadoria."),
        Filme("Demon Slayer: Kimetsu no Yaiba – O Filme: Mugen Train", R.drawable.demonslayer, "Tanjiro e seus amigos se juntam ao Hashira das Chamas, Kyojuro Rengoku, para investigar o desaparecimento de pessoas em um trem misterioso."),
        Filme("A Longa Marcha: Caminhe ou Morra", R.drawable.longamacha, "Em um futuro distópico, jovens competem em uma caminhada brutal sem parar, onde o último sobrevivente ganha um grande prêmio."),
        Filme("A Vida de Chuck", R.drawable.vidachuck, "A história da vida de Charles 'Chuck' Krantz contada em ordem cronológica inversa, começando com sua morte e retrocedendo até sua infância."),
        Filme("O Pior Homem de Londres", R.drawable.piorhomemlondres, "A história de Charles Augustus Howell, um negociante de arte e mestre da chantagem na Londres vitoriana, que inspirou o criador de Sherlock Holmes.")
    )

    val filme = filmes.find { it.nome == filmeNome }

    filme?.let {
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

@Composable
fun MenuItemEntretenimento(
    onClick: () -> Unit,
    text: String,
    selected: Boolean
) {
    val backgroundColor = if (selected) Color(0xFFDCD80A) else Color(0xFF757900)

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