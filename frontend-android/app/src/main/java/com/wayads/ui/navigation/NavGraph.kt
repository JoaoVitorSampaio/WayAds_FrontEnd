package com.wayads.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wayads.ui.atualidades.AtualidadesScreen
import com.wayads.ui.entretenimento.EntretenimentoScreen
import com.wayads.ui.gastronomia.GastronomiaScreen
import com.wayads.ui.home.HomeScreen
import com.wayads.ui.kids.KidsScreen
import com.wayads.ui.turismo.TurismoScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("atualidades") { AtualidadesScreen() }
        composable("kids") { KidsScreen() }
        composable("turismo") { TurismoScreen() }
        composable("gastronomia") { GastronomiaScreen() }
        composable("entretenimento") { EntretenimentoScreen() }
    }
}