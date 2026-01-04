package com.example.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presentation.bookmarks.BookmarksViewModel

@Composable
fun BookmarksScreen(
    viewModel: BookmarksViewModel,
    onMovieClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(vertical = 50.dp, horizontal = 16.dp)
    ) {

        if (state.movies.isEmpty()) {
            item {
                EmptyBookmarks()
            }
        } else {
            items(
                items = state.movies,
                key = { it.id }
            ) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    onBookmark = {}
                )
            }
        }
    }
}

@Composable
private fun EmptyBookmarks() {
    Text(
        text = "No saved movies",
        modifier = Modifier.padding(24.dp)
    )
}

