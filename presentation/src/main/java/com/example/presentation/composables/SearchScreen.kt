package com.example.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.example.domain.Movie
import com.example.presentation.search.SearchIntent
import com.example.presentation.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val state by viewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val query = remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.padding(vertical = 50.dp, horizontal = 20.dp)
    ) {
        SearchInput(
            query = query.value,
            onQueryChange = {
                query.value = it
                viewModel.dispatch(SearchIntent.QueryChanged(it))
            },
            focusRequester = focusRequester
        )

        SearchResults(
            movies = state.results
        )
    }
}


@Composable
private fun SearchInput(
    query: String,
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        placeholder = { Text("Search movies...") },
        singleLine = true
    )
}

@Composable
private fun SearchResults(
    movies: List<Movie>
) {
    LazyColumn {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieCard(
                movie = movie,
                onClick = {},
                onBookmark = {}
            )
        }
    }
}

