package com.example.transportistaapp.di

import com.example.transportistaapp.data.RepositoryImpl
import com.example.transportistaapp.data.network.UsersDao
import com.example.transportistaapp.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    @Singleton
    @Provides
    fun proveerFirestore() = FirebaseFirestore.getInstance()

    @Provides
    fun proveerRepositorio(usersDao:UsersDao, firebaseFirestore: FirebaseFirestore, firebaseAuth:FirebaseAuth) : Repository {
        return RepositoryImpl(usersDao, firebaseFirestore, firebaseAuth)
    }
}