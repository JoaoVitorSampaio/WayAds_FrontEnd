package com.wayads.ui.gastronomia

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import com.wayads.ui.gastronomia.model.GastronomiaData
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(navController: NavController, recipeId: Int?) {
    val receita = remember(recipeId) {
        GastronomiaData.receitas.firstOrNull { it.id == recipeId }
    }

    var alpha by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        // Animação simples de fade-in (transição suave)
        for (i in 0..10) {
            alpha = i / 10f
            delay(16)
        }
    }

    if (receita == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Receita") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF2d2d2d),
                        titleContentColor = Color.White
                    )
                )
            },
            containerColor = Color.Black
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Receita não encontrada.", color = Color.White)
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(receita.titulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2d2d2d),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .graphicsLayer(alpha = alpha)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = receita.imagemRes),
                contentDescription = receita.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoChip("⏱️ ${receita.tempoPreparo}")
                InfoChip("🍽️ ${receita.rendimento}")
                InfoChip("💪 ${receita.dificuldade}")
            }

            Spacer(Modifier.height(20.dp))

            SectionTitle("Ingredientes")
            Spacer(Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                receita.ingredientes.forEach { item ->
                    Row(verticalAlignment = Alignment.Top) {
                        Text("• ", color = Color(0xFFFF6B35), fontWeight = FontWeight.Bold)
                        Text(item, color = Color(0xFFDDDDDD))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            SectionTitle("Modo de preparo")
            Spacer(Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                receita.modoPreparo.forEachIndexed { index, passo ->
                    Row {
                        Text("${index + 1}. ", color = Color(0xFFFF6B35), fontWeight = FontWeight.Bold)
                        Text(passo, color = Color(0xFFDDDDDD))
                    }
                }
            }

            receita.destaque?.let {
                Spacer(Modifier.height(20.dp))
                Text("Dica: $it", color = Color(0xFFFF6B35), fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun InfoChip(text: String) {
    Surface(
        color = Color(0xFF1A1A1A),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = null
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )
    }
}