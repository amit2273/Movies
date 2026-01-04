package com.example.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Bookmarks : Screen("bookmarks")
    object Details : Screen("details/{movieId}") {
        fun create(id: Int) = "details/$id"
    }
}
