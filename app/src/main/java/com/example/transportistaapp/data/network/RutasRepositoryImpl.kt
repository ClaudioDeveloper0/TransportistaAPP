package com.example.transportistaapp.data.network

import com.example.transportistaapp.data.RepositoryImpl
import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import com.example.transportistaapp.data.network.RutasApi
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.util.Date

class RutasRepositoryImpl(
    private val rutasApi: RutasApi
) : Repository {
    override suspend fun loginTransportista(
        user: String,
        password: String
    ): FirebaseUser? {
        TODO("Not yet implemented")
    }

    // Obtiene las rutas activas para un transportista
    override suspend fun getRutasActivas(uid: String): List<Ruta> {
        return try {
            rutasApi.getRutasActivas(uid)  // Llamada a la API para obtener las rutas activas
        } catch (e: HttpException) {
            throw Exception("Error al obtener las rutas activas: ${e.message}")
        }
    }

    override suspend fun marcarCajaEntregada(cajaId: String, fechaEntrega: Date) {
        TODO("Not yet implemented")
    }

    override suspend fun marcarCajaNoEntregada(cajaId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerPaquetesNoEntregados(): List<Paquete>? {
        TODO("Not yet implemented")
    }

    override suspend fun terminarEntrega() {
        TODO("Not yet implemented")
    }

    // Obtiene los paquetes asociados a una ruta específica
    override suspend fun getPaquetesByRoute(routeId: String): List<Paquete> {
        return try {
            rutasApi.getPaquetesByRoute(routeId)  // Llamada a la API para obtener los paquetes
        } catch (e: HttpException) {
            throw Exception("Error al obtener los paquetes para la ruta: ${e.message}")
        }
    }

    // Actualiza el estado de un paquete
    override suspend fun updatePaqueteStatus(paqueteId: String, status: String) {
        return try {
            val paquete = Paquete(
                paqueteId, status,
                ruta = TODO(),
                contacto = TODO(),
                receptor = TODO(),
                estado = TODO(),
                fecha = TODO(),
                detalles = TODO()
            )  // Crear objeto con el estado nuevo
            rutasApi.updatePaqueteStatus(paquete)  // Llamada a la API para actualizar el estado del paquete
        } catch (e: HttpException) {
            throw Exception("Error al actualizar el estado del paquete: ${e.message}")
        }
    }
}
