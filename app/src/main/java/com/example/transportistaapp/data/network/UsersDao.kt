package com.example.transportistaapp.data.network

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersDao @Inject constructor( private val db: FirebaseFirestore) {

    suspend fun esTransportista(user: FirebaseUser): Boolean {
        return try {
            val document = db.collection("Usuarios").document(user.uid).get().await()
            val tipo = document.get("tipo") as? Long
            tipo == 0L
        } catch (e: Exception) {
            Log.d(TAG, "Error fetching document: ", e)
            false
        }
    }
}