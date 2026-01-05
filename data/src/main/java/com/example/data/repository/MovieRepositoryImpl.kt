package com.example.data.repository

import android.util.Log
import com.example.data.TmdbApi
import com.example.data.db.MovieDao
import com.example.data.db.mapper.toDomain
import com.example.data.db.mapper.toEntity
import com.example.data.db.mapper.toEntityPreservingBookmark
import com.example.domain.Movie
import com.example.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

internal class MovieRepositoryImpl(
    private val api: TmdbApi,
    private val dao: MovieDao,
    private val apiKey: String
) : MovieRepository {

    override fun trendingMovies(): Flow<List<Movie>> =
        dao.moviesByCategory("TRENDING")
            .onStart {
                fetchTrendingFromApi()
            }
            .filter { entities ->
                entities.isNotEmpty()
            }
            .map { it.toDomain() }

    override fun nowPlayingMovies(): Flow<List<Movie>> =
        dao.moviesByCategory("NOW_PLAYING")
            .onStart {
                fetchNowPlayingFromApi()
            }
            .filter { entities ->
                entities.isNotEmpty()
            }
            .map { it.toDomain() }

    override fun bookmarkedMovies(): Flow<List<Movie>> =
        dao.bookmarkedMovies()
            .map { it.toDomain() }

    override suspend fun searchMovies(query: String): List<Movie> =
        try {
            api.search(query, apiKey)
                .body()
                ?.results
                ?.map { it.toDomain() }
                ?: emptyList()
        } catch (e: Exception) {
            Log.e("Repository", "Search failed", e)
            emptyList()
        }

    override suspend fun movieDetails(movieId: Int): Movie {
        dao.movieById(movieId)?.let {
            return it.toDomain()
        }

        try {
            val response = api.details(movieId, apiKey)
            if (response.isSuccessful) {
                val entity = response.body()!!.toEntity(category = "DETAILS")
                dao.insertMovies(listOf(entity))
                return entity.toDomain()
            }
        } catch (e: Exception) {
            Log.e("Repository", "Details fetch failed", e)
        }

        throw IllegalStateException("Movie not available offline")
    }

    override suspend fun bookmarkMovie(movieId: Int, bookmarked: Boolean) {
        dao.updateBookmark(movieId, bookmarked)
    }

    private suspend fun fetchTrendingFromApi() {
        try {
            val response = api.trending(apiKey)
            if (response.isSuccessful) {
                val entities = response.body()?.results
                    ?.map {
                        it.toEntityPreservingBookmark(
                            dao = dao,
                            category = "TRENDING"
                        )
                    }
                    ?: emptyList()

                dao.insertMovies(entities)
            }
        } catch (e: Exception) {
            Log.e("Repository", "Trending fetch failed", e)
        }
    }

    private suspend fun fetchNowPlayingFromApi() {
        try {
            val response = api.nowPlaying(apiKey)
            if (response.isSuccessful) {
                val entities = response.body()?.results
                    ?.map { it.toEntity("NOW_PLAYING") }
                    ?: emptyList()

                dao.insertMovies(entities)
            }
        } catch (e: Exception) {
            Log.e("Repository", "Now playing fetch failed", e)
        }
    }
}




