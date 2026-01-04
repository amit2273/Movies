package com.example.data.di

import androidx.room.Room
import com.example.data.db.AppDatabase
import com.example.data.db.MovieDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movies.db"
        ).fallbackToDestructiveMigration()
         .build()
    }

    single<MovieDao> {
        get<AppDatabase>().movieDao()
    }
}
