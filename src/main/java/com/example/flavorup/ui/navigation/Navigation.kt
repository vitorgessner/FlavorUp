package com.example.flavorup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flavorup.ui.screens.detail.DetailScreen
import com.example.flavorup.ui.screens.favorites.FavoritesScreen
import com.example.flavorup.ui.screens.home.HomeScreen
import com.example.flavorup.ui.screens.search.SearchScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search/{ingredients}") {
        fun createRoute(ingredients: List<String>): String {
            val ingredientsParam = ingredients.joinToString(",")
            return "search/$ingredientsParam"
        }
    }
    object Detail : Screen("detail/{recipeId}") {
        fun createRoute(recipeId: Int): String {
            return "detail/$recipeId"
        }
    }
    object Favorites : Screen("favorites")
}

@Composable
fun FlavorUpNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onSearchClick = { ingredients ->
                    navController.navigate(Screen.Search.createRoute(ingredients))
                },
                onFavoritesClick = {
                    navController.navigate(Screen.Favorites.route)
                }
            )
        }

        composable(
            route = Screen.Search.route,
            arguments = listOf(
                navArgument("ingredients") { type = NavType.StringType }
            )
        ) {
            SearchScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.Detail.createRoute(recipeId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.StringType }
            )
        ) {
            DetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.Detail.createRoute(recipeId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}