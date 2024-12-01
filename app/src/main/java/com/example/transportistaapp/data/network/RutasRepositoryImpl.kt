package com.example.transportistaapp.data.network

import com.example.transportistaapp.domain.model.RutaT
import com.example.transportistaapp.domain.useCases.RutasRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RutasRepositoryImpl(private val firestore: FirebaseFirestore) : RutasRepository {
    override suspend fun getRutasActivas(uid: String): List<RutaT> {
        val rutasSnapshot = firestore.collection("rutas")
            .whereEqualTo("uid", uid)
            .get()
            .await()

        return rutasSnapshot.documents.map { document ->
            RutaT(
                id = document.id,
                nombre = document.getString("nombre") ?: "",
                cargado = document.getBoolean("cargado") ?: false,
                validado = document.getBoolean("validado") ?: false
            )
        }
    }
}
