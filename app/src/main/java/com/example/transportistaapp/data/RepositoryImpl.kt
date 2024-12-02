package com.example.transportistaapp.data

import com.example.transportistaapp.data.network.FirestoreService
import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Ruta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firestoreService: FirestoreService,
    private val firebaseAuth: FirebaseAuth

) : Repository{
    override suspend fun loginTransportista(user: String, password: String): FirebaseUser? {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(user, password).await()
            if (result.user != null){
                if (firestoreService.esTransportista(result.user!!)){
                    return result.user
                }
            }
            return null
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getRutasActivas(uid: String): List<Ruta> {
        return firestoreService.obtenerRutasPorTrabajador(uid)
    }
}