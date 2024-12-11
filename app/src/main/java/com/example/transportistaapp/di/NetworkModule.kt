package com.example.transportistaapp.di

import com.example.transportistaapp.data.network.RutasApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://firestore.googleapis.com/v1/projects/%3Cproject-id%3E/databases/(default)/documents/Ruta/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRutasApi(retrofit: Retrofit): RutasApi {
        return retrofit.create(RutasApi::class.java)
    }
}
