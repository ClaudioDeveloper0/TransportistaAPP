package com.example.transportistaapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Verificar si hay un usuario autenticado
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    // Iniciar sesión con correo y contraseña
    suspend fun login(email: String, password: String): FirebaseUser? {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return result.user
        } catch (e: Exception) {
            throw e
        }
    }

    // Cerrar sesión
    fun logout() {
        firebaseAuth.signOut()
    }

}
