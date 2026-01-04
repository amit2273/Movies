package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.presentation.composables.BookmarksScreen
import com.example.presentation.composables.DetailsScreen
import com.example.presentation.composables.HomeScreen
import com.example.presentation.composables.SearchScreen
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = koinViewModel(),
                onMovieClick = {
                    navController.navigate(Screen.Details.create(it))
                }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(viewModel = koinViewModel())
        }

        composable(Screen.Bookmarks.route) {
            BookmarksScreen(
                viewModel = koinViewModel(),
                onMovieClick = {
                    navController.navigate(Screen.Details.create(it))
                }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("movieId") {
                type = NavType.IntType
            }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "movies://details/{movieId}"
                }
            )
        ) { backStack ->
            DetailsScreen(
                movieId = backStack.arguments!!.getInt("movieId"),
                viewModel = koinViewModel()
            )
        }
    }
}
