package com.example.transportistaapp.data.network

import android.content.ContentValues.TAG
import android.util.Log
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.WriteBatch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreService @Inject constructor(private val db: FirebaseFirestore) {

    suspend fun obtenerRutasPorTrabajador(uid: String): List<Ruta> {
        val rutasSnapshot = db.collection("Rutas")
            .whereEqualTo("transportista", uid)
            .whereEqualTo("activa", true)
            .get()
            .await()

        val rutas = rutasSnapshot.documents.map { d ->
            Ruta(
                id = d.id,
                nombre = d.getString("alias") ?: "",
                cargado = d.getBoolean("cargado") ?: false,
                completado = d.getBoolean("validado") ?: false,
                enReparto = d.getBoolean("en_reparto") ?: false
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

    suspend fun terminarRutas(rutas: List<Ruta>) {
        rutas.forEach {
            val data = hashMapOf(
                "en_reparto" to false,
                "activa" to false,
                "cargado" to false,
                "completado" to true,
            )
            db.collection("Rutas").document(it.id)
                .set(data, SetOptions.merge()).await()
        }
    }

    suspend fun terminarPaquetes(paquetes: List<Paquete>) {
        val batchSize = 500
        paquetes.chunked(batchSize).forEach { paqueteChunk ->
            val batch: WriteBatch = db.batch()
            paqueteChunk.forEach { paquete ->
                val docRef = db.collection("Rutas").document(paquete.id)
                val historial = hashMapOf(
                    "detalles" to paquete.detalles,
                    "estado" to paquete.estado,
                    "fecha" to Timestamp(paquete.fecha)
                )
                batch.update(
                    docRef,
                    "estado", paquete.estado,
                    "contacto", paquete.contacto,
                    "receptor", paquete.receptor,
                    "historial", FieldValue.arrayUnion(historial)
                )
            }
            batch.commit().await()
        }
    }

}