package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE category = :category")
    fun moviesByCategory(category: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE bookmarked = 1")
    fun bookmarkedMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("UPDATE movies SET bookmarked = :value WHERE id = :movieId")
    suspend fun updateBookmark(movieId: Int, value: Boolean)

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun movieById(movieId: Int): MovieEntity?

    @Query("SELECT bookmarked FROM movies WHERE id = :movieId")
    suspend fun isBookmarked(movieId: Int): Boolean?
}
