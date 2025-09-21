package com.wayads.ui.entretenimento

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.wayads.app.R
import com.wayads.data.model.Movie
import com.wayads.ui.entretenimento.viewmodel.EntretenimentoViewModel
import com.wayads.ui.entretenimento.viewmodel.MovieUiState

enum class EntretenimentoCategoria(val label: String) {
    FILMES("Filmes em cartaz"),
    MUSICAS("Músicas"),
    ESPORTES("Esportes"),
    EVENTOS("Eventos & Cultura")
}

@Composable
fun EntretenimentoScreen(navController: NavController, viewModel: EntretenimentoViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val window = (LocalView.current.context as Activity).window
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentBrightness by remember { mutableStateOf(window.attributes.screenBrightness.takeIf { it >= 0.0f } ?: 0.5f) }
    var showVolumeDialog by remember { mutableStateOf(false) }
    var showBrightnessDialog by remember { mutableStateOf(false) }

    var categoriaSelecionada by remember { mutableStateOf(EntretenimentoCategoria.FILMES) }

    if (categoriaSelecionada == EntretenimentoCategoria.FILMES) {
        viewModel.fetchMovies()
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
                        val uiState by viewModel.uiState.collectAsState()
                        NavHost(
                            navController = innerNavController,
                            startDestination = "listaFilmes",
                            modifier = Modifier.fillMaxSize()
                        ) {
                            composable("listaFilmes") { ListaDeFilmes(innerNavController, uiState) }
                            composable("detalheFilme/{filmeNome}") { backStackEntry ->
                                val filmeNome = backStackEntry.arguments?.getString("filmeNome") ?: ""
                                DetalheFilmeScreen(filmeNome, innerNavController, viewModel)
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
fun ListaDeFilmes(navController: NavController, uiState: MovieUiState) {
    when (uiState) {
        is MovieUiState.Loading -> {
            CircularProgressIndicator()
        }
        is MovieUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.movies) { movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clickable { navController.navigate("detalheFilme/${movie.title}") },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize().padding(8.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(movie.posterUrl),
                                contentDescription = movie.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(160.dp)
                                    .fillMaxHeight()
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(movie.title, color = Color.White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
        is MovieUiState.Error -> {
            Text(text = uiState.message, color = Color.Red)
        }
    }
}

@Composable
fun DetalheFilmeScreen(filmeNome: String, navController: NavController, viewModel: EntretenimentoViewModel) {
    val movie = viewModel.getMovieByTitle(filmeNome)

    movie?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(it.posterUrl),
                contentDescription = it.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(it.title, fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(it.description, fontSize = 16.sp, color = Color.LightGray)
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