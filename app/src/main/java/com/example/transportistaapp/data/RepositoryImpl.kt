package com.example.transportistaapp.data

import com.example.transportistaapp.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    //Recibir las clases DAO de la base de datos
    private val firebaseAuth: FirebaseAuth

) : Repository{
    override suspend fun loginTransportista(user: String, password: String): FirebaseUser? {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(user, password).await()
            return result.user
        } catch (e: Exception) {
            throw e
        }
    }
}