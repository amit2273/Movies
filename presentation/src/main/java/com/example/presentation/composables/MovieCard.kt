package com.example.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.Movie
import com.example.domain.TmdbConfig

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    onBookmark: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {

            AsyncImage(
                model = TmdbConfig.IMAGE_BASE_URL + movie.poster,
                contentDescription = movie.title,
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(movie.title, fontWeight = FontWeight.Bold)
                Text(movie.overview, maxLines = 2)
            }

            IconButton(onClick = onBookmark) {
                Icon(
                    imageVector = if (movie.isBookmarked)
                        Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = "Bookmark"
                )
            }
        }
    }
}
