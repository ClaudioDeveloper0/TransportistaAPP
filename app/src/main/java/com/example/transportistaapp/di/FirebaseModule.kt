package com.example.transportistaapp.di

import com.example.transportistaapp.data.RepositoryImpl
import com.example.transportistaapp.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun proveerFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun proveerRepositorio(firebaseAuth:FirebaseAuth) : Repository {
        return RepositoryImpl(firebaseAuth)
    }
}