package com.example.data.db.mapper

import com.example.data.MovieDto
import com.example.data.db.MovieDao
import com.example.data.db.MovieEntity
import com.example.domain.Movie

fun MovieDto.toEntity(category: String) = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    bookmarked = false,
    category = category
)


fun List<MovieDto>.toEntity(category: String) =
    map { it.toEntity(category) }


fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    poster = posterPath,
    overview = overview,
    isBookmarked = bookmarked
)

fun List<MovieEntity>.toDomain() =
    map { it.toDomain() }


fun MovieDto.toDomain() = Movie(
    id = id,
    title = title,
    poster = posterPath,
    overview = overview,
    isBookmarked = false
)

suspend fun MovieDto.toEntityPreservingBookmark(
    dao: MovieDao,
    category: String
): MovieEntity {
    val bookmarked = dao.isBookmarked(id) ?: false
    return MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview,
        bookmarked = bookmarked,
        category = category
    )
}


