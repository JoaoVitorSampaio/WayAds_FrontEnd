package com.wayads.ui.gastronomia.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.wayads.ui.gastronomia.model.*

/**
 * Item do menu lateral da tela de gastronomia
 */
@Composable
fun GastronomiaMenuItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected && text == "Voltar" -> Color(0xFFFF5722)   // Cor laranja para "Voltar" quando selecionado
        isSelected -> Color(0xFFD32F2F)                       // Cor vermelha para outros botões quando selecionados
        else -> Color(0xFF8B0000)                             // Cor vermelha escura para botões não selecionados
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

/**
 * Card para exibir anúncios gastronômicos com foco em promoções
 */
@Composable
fun AnuncioCard(
    anuncio: AnuncioGastronomico,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2d2d2d)),
        border = BorderStroke(2.dp, Color(0xFFFF6B35)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Badge de desconto (se houver)
            anuncio.desconto?.let { desconto ->
                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFFFF6B35),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = desconto,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Imagem do anúncio
            Image(
                painter = painterResource(id = anuncio.imagemRes),
                contentDescription = anuncio.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Título do anúncio
            Text(
                text = anuncio.titulo,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Promoção destacada
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color(0xFFFF6B35))
            ) {
                Text(
                    text = "🎉 ${anuncio.promocao}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFF6B35),
                    modifier = Modifier.padding(12.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Prato especial
            Text(
                text = "🍽️ Prato Especial: ${anuncio.pratoEspecial}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(6.dp))
            
            // Experiência única
            Text(
                text = "✨ ${anuncio.experienciaUnica}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFCCCCCC),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Preço e validade
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                anuncio.preco?.let { preco ->
                    Text(
                        text = preco,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
                
                anuncio.validadePromocao?.let { validade ->
                    Text(
                        text = "⏰ $validade",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCCCCCC)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Categoria e região
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = anuncio.categoria.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFFF5722),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = anuncio.regiao.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFCCCCCC)
                )
            }
        }
    }
}

/**
 * Card para exibir receitas gastronômicas
 */
@Composable
fun ReceitaCard(
    receita: ReceitaGastronomica,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2d2d2d)),
        border = BorderStroke(2.dp, Color(0xFFFF6B35)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = receita.imagemRes),
                contentDescription = receita.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = receita.titulo,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF1A1A1A), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "⏱️ ${'$'}{receita.tempoPreparo}",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFF1A1A1A), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "🍽️ ${'$'}{receita.rendimento}",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val ingredientesPreview = remember(receita) {
                val base = receita.ingredientes.take(3).joinToString(" • ")
                if (receita.ingredientes.size > 3) "$base • ..." else base
            }
            Text(
                text = "Ingredientes: ${'$'}ingredientesPreview",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFCCCCCC)
            )

            Spacer(modifier = Modifier.height(6.dp))

            val preparoPreview = remember(receita) {
                val base = receita.modoPreparo.take(2).joinToString(" → ")
                if (receita.modoPreparo.size > 2) "$base → ..." else base
            }
            Text(
                text = "Preparo: ${'$'}preparoPreview",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFCCCCCC)
            )

            receita.destaque?.let { destaque ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "⭐ ${'$'}destaque",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFFF6B35),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/**
 * Componente de dropdown para filtros
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FilterDropdown(
    label: String,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    getDisplayName: (T) -> String = { 
        when (it) {
            is RegiaoGeografica -> it.displayName
            is CategoriaComida -> it.displayName
            else -> it.toString()
        }
    }
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = label,
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.White
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF5722),
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF2d2d2d))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = getDisplayName(option),
                            color = Color.White
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Color.White
                    )
                )
            }
        }
    }
}

/**
 * Dialog para controles de volume e brilho
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
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2d2d2d)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDecrease,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = decreaseIcon,
                            contentDescription = "Diminuir $title",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = onIncrease,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = increaseIcon,
                            contentDescription = "Aumentar $title",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}