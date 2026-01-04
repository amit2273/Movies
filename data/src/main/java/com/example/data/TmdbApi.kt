package com.example.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("trending/movie/day")
    suspend fun trending(@Query("api_key") key: String): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun nowPlaying(@Query("api_key") key: String): Response<MovieResponse>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("api_key") key: String
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun details(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ): Response<MovieDto>
}
