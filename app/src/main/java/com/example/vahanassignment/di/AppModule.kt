package com.example.vahanassignment.di

import android.content.Context
import androidx.room.Room
import com.example.vahanassignment.Database.UniversityDatabase
import com.example.vahanassignment.data.UniversityRepository
import com.example.vahanassignment.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Provides ApiService instance for API communication
    @Singleton
    @Provides
    fun provideApi(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://universities.hipolabs.com")
            .build()
            .create(ApiService::class.java)
    }

    // Provides FeedDatabase instance for database operations
    @Provides
    @Singleton
    fun provideFeedDatabase(@ApplicationContext app: Context): UniversityDatabase {
        return Room.databaseBuilder(app, UniversityDatabase::class.java, "Feed")
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(
        api: ApiService,
        db: UniversityDatabase
    ): UniversityRepository {
        return UniversityRepository(api, db)
    }
}