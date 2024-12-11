package com.example.transportistaapp.data.network

import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Paquete
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date
import javax.inject.Inject
class RutasRepositoryImpl @Inject constructor(
    private val rutasApi: RutasApi // Inyectando RutasApi
) : Repository {
    override suspend fun loginTransportista(
        user: String,
        password: String
    ): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun getRutasActivas(uid: String) = rutasApi.getRutasActivas(uid)
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

    override suspend fun getPaquetesByRoute(routeId: String) = rutasApi.getPaquetesByRoute(routeId)

    override suspend fun updatePaqueteStatus(paqueteId: String, status: String) {
        rutasApi.updatePaqueteStatus(paqueteId)
    }
}

