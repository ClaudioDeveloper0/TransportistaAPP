package com.example.transportistaapp.data.network

import android.content.ContentValues.TAG
import android.util.Log
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreService @Inject constructor(private val db:FirebaseFirestore){

    suspend fun obtenerRutasPorTrabajador(uid: String) : List<Ruta>{
        val rutasSnapshot = db.collection("Rutas")
            .whereEqualTo("transportista", uid)
            .get()
            .await()

        val rutas = rutasSnapshot.documents.map { d ->
            Ruta(
                id = d.id,
                nombre = d.getString("alias") ?: "",
                cargado = d.getBoolean("cargado") ?: false,
                validado = d.getBoolean("validado") ?: false
            )
        }

        val rutasIds = rutas.map { it.id }

        val rutasChunks = rutasIds.chunked(10) // Divide la lista en partes de máximo 10 elementos
        val paquetes = mutableListOf<Paquete>()

        for (chunk in rutasChunks) {
            val paquetesSnapshot = db.collection("Paquetes")
                .whereIn("ruta", chunk)
                .get()
                .await()
            paquetes.addAll(paquetesSnapshot.documents.map { p ->
                Paquete(
                    id = p.id,
                    contacto = p.getString("contacto") ?: "",
                    direccion = p.getString("direccion") ?: "",
                    receptor = p.getString("receptor") ?: "",
                    ruta = p.getString("ruta") ?: "",
                    estado = when (p.getLong("estado") ?: 0L) {
                        0L -> "Salió del centro de distribución"
                        1L -> "Recepcionado por empresa transportista"
                        2L -> "En reparto"
                        3L -> "Entregado"
                        4L -> "Falla en entrega, devuelto a empresa transportista"
                        5L -> "Falla en entrega, devuelto al vendedor"
                        else -> "Estado desconocido"
                    }
                )
            })
        }

        val paquetesPorRuta = paquetes.groupBy { it.ruta }

        rutas.forEach { ruta ->
            ruta.paquetes = paquetesPorRuta[ruta.id] ?: emptyList()
        }
        Log.d("maybeaLog", rutas.toString())

        return rutas

    }
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