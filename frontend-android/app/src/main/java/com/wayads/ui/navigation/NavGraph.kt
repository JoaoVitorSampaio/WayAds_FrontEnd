package com.wayads.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wayads.ui.atualidades.AtualidadesScreen
import com.wayads.ui.atualidades.NoticiaDetailScreen
import com.wayads.ui.entretenimento.EntretenimentoScreen
import com.wayads.ui.gastronomia.GastronomiaScreen
import com.wayads.ui.gastronomia.RecipeDetailScreen
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
        composable("atualidades") { AtualidadesScreen(navController) }
        composable(
            "noticiaDetail/{noticiaId}",
            arguments = listOf(navArgument("noticiaId") { type = NavType.IntType })
        ) {
            NoticiaDetailScreen(navController = navController)
        }
        composable("kids") { KidsScreen(navController) }
        composable("turismo") { TurismoScreen(navController) }
        composable("gastronomia") { GastronomiaScreen(navController) }
        composable("entretenimento") { EntretenimentoScreen(navController) }
        composable(
            "receita/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            RecipeDetailScreen(navController = navController, recipeId = id)
        }
    }
}
