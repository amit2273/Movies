package com.example.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.presentation.home.HomeIntent
import com.example.presentation.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.dispatch(HomeIntent.Load)
    }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 50.dp),
        contentPadding = PaddingValues(vertical = 50.dp)
    ) {

        if (state.trending.isNotEmpty()) {

            stickyHeader {
                SectionHeader("Trending")
            }

            items(
                items = state.trending,
                key = { it.id }
            ) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    onBookmark = {
                        viewModel.dispatch(
                            HomeIntent.ToggleBookmark(
                                movie.id,
                                !movie.isBookmarked
                            )
                        )
                    }
                )
            }
        }

        if (state.nowPlaying.isNotEmpty()) {

            stickyHeader {
                SectionHeader("Now Playing")
            }

            items(
                items = state.nowPlaying,
                key = { it.id }
            ) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    onBookmark = {
                        viewModel.dispatch(
                            HomeIntent.ToggleBookmark(
                                movie.id,
                                !movie.isBookmarked
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.Yellow,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
