package com.app.youtubeclone.di

import com.app.youtubeclone.data.repository.VideoRepositoryImpl
import com.app.youtubeclone.domain.repository.VideosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    const val BASE_URL = "https://ymkrctbgaigwutenrevi.supabase.co"
    const val BASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inlta3JjdGJnYWlnd3V0ZW5yZXZpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTQ5NzE5NjksImV4cCI6MjAzMDU0Nzk2OX0.oK2cgxwoJhrJ8gB3dyDzhmtsKIuZCmT0ZRuN5wg0PaE"
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient{
        return createSupabaseClient(
            supabaseUrl = BASE_URL,
            supabaseKey = BASE_KEY,
        ){
            install(Postgrest)
        }
    }
}