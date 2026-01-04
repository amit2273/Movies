package com.example.data.di

import com.example.data.TmdbApi
import com.example.domain.TmdbConfig
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(TmdbConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<TmdbApi> {
        get<Retrofit>().create(TmdbApi::class.java)
    }

    // API Key (kept injectable for testing)
    single(named("TMDB_API_KEY")) {
        "7cf8e61f8425f6db4175317ecf03f8ca"
    }
}
