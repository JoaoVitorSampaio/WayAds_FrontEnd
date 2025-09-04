package com.wayads.mobile_app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    // Top-level Row for landscape orientation: Main Content (left) and Menu (right)
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green) // Example background for the main ad area
    ) {
        // 1. Main Content Area (Large Ad) - Left side
        Column(
            modifier = Modifier
                .weight(1f) // Takes up available space
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // TODO: Implement the large ad content here
            // - Título em destaque: "DUPLA FANTÁSTICA"
            // - Texto secundário: "Leve a dupla e pague só o Sub."
            // - Imagem do produto: sanduíche + bebida (Fanta Guaraná)
            // - Marca do anunciante: Subway, com slogan "Faça como quiser."
            Text(text = "DUPLA FANTÁSTICA", style = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 24.sp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Leve a dupla e pague só o Sub.", style = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 16.sp))
            Spacer(modifier = Modifier.height(16.dp))
            // TODO: Add Image Composable for product and brand
            Text(text = "IMAGEM DO PRODUTO AQUI", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "MARCA DO ANUNCIANTE AQUI", color = Color.White)
        }

        // 2. Right-side Menu
        Column(
            modifier = Modifier
                .width(200.dp) // Fixed width for the menu
                .fillMaxHeight()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // TODO: Implement 5 rectangular buttons for categories
            // - Atualidades (roxo)
            // - Kids (azul)
            // - Turismo (verde)
            // - Gastronomia (vermelho)
            // - Entretenimento (amarelo)
            CategoryButton(text = "Atualidades", color = Color.Magenta)
            CategoryButton(text = "Kids", color = Color.Blue)
            CategoryButton(text = "Turismo", color = Color.Green)
            CategoryButton(text = "Gastronomia", color = Color.Red)
            CategoryButton(text = "Entretenimento", color = Color.Yellow)
        }
    }

    // 3. Footer (positioned at the bottom, spanning full width)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 0.dp), // Adjust padding as needed
        contentAlignment = Alignment.BottomCenter
    ) {
        // TODO: Implement the footer content
        // - Barra horizontal em cinza claro
        // - Texto em destaque: "ANUNCIE AQUI!" (vermelho)
        // - Texto menor: "anúncio em barra estático ou dinâmico" (preto)
        HomeFooter()
    }

    // 4. Action Icons (Volume, Brightness) - Bottom Right
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            // TODO: Add actual icons for Volume and Brightness
            Text(text = "Volume Icon", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Brightness Icon", color = Color.Gray)
        }
    }
}

@Composable
fun CategoryButton(text: String, color: Color) {
    Button(
        onClick = { /* TODO: Handle category click */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = text, color = Color.White)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HomeFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ANUNCIE AQUI!", color = Color.Red, style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp))
        Text(text = "anúncio em barra estático ou dinâmico", color = Color.Black, style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp))
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360) // Landscape preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
