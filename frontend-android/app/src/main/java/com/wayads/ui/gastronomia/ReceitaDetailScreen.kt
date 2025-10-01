package com.wayads.ui.gastronomia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wayads.ui.gastronomia.viewmodel.ReceitaDetailViewModel

@Composable
fun ReceitaDetailScreen(
    navController: NavController,
    viewModel: ReceitaDetailViewModel = hiltViewModel()
) {
    val receita by viewModel.receita.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a1a)),
        contentAlignment = Alignment.Center
    ) {
        receita?.let { r ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = "http://10.0.2.2:8081" + r.imagemUrl,
                    contentDescription = r.nome,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(r.nome, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text("Ingredientes", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    r.ingredientes.forEach { ingrediente ->
                        Text("• $ingrediente", color = Color.LightGray, fontSize = 16.sp)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Modo de Preparo", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    r.modoPreparo.forEachIndexed { index, passo ->
                        Text("${index + 1}. $passo", color = Color.LightGray, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Voltar")
                    }
                }
            }
        } ?: run {
            CircularProgressIndicator()
        }
    }
}
