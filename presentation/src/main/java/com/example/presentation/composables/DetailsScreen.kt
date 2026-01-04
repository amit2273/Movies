package com.example.presentation.composables

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.example.domain.Movie
import com.example.domain.TmdbConfig
import com.example.presentation.details.DetailsEffect
import com.example.presentation.details.DetailsIntent
import com.example.presentation.details.DetailsViewModel

@Composable
fun DetailsScreen(
    movieId: Int,
    viewModel: DetailsViewModel
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(movieId) {
        viewModel.dispatch(DetailsIntent.Load(movieId))
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(
            Lifecycle.State.STARTED
        ){
            viewModel.effect.collect { effect ->
                when (effect) {
                    is DetailsEffect.ShareMovie -> {
                        shareMovie(context, effect.movie)
                    }
                }
            }
        }
    }

    state.movie?.let { movie ->
        Column(Modifier.padding(vertical = 50.dp, horizontal = 16.dp)) {

            AsyncImage(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                model = TmdbConfig.IMAGE_BASE_URL + movie.poster,
                contentDescription = movie.title
            )

            Text(movie.title, style = MaterialTheme.typography.titleLarge)
            Text(movie.overview)

            Row {
                Button(onClick = {
                    Toast.makeText(context,"Bookmarked ${movie.isBookmarked.not()}", Toast.LENGTH_SHORT).show()
                    viewModel.dispatch(
                        DetailsIntent.ToggleBookmark(
                            movie.id,
                            !movie.isBookmarked
                        )
                    )
                }) {
                    Text(text = "Bookmark")
                }

                Spacer(Modifier.width(8.dp))

                Button(onClick = {
                    viewModel.dispatch(DetailsIntent.Share)
                }) {
                    Text("Share")
                }
            }
        }
    }
}

private fun shareMovie(
    context: Context,
    movie: Movie
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            "Check out this movie: ${movie.title}\n" +
                    "movies://details/${movie.id}"
        )
    }

    context.startActivity(
        Intent.createChooser(intent, "Share movie")
    )
}
