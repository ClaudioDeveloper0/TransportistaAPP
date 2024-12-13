package com.example.transportistaapp.domain

import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import com.google.firebase.auth.FirebaseUser
import java.util.Date

interface Repository {
    suspend fun loginTransportista(user:String, password:String) : FirebaseUser?

    suspend fun updateLocalPackages(transportista:String)

    suspend fun getRutasActivas(uid: String): List<Ruta>

    suspend fun marcarCajaEntregada(cajaId : String, fechaEntrega : Date)

    suspend fun marcarCajaNoEntregada(cajaId : String)

    suspend fun obtenerPaquetesNoEntregados() : List<Paquete>?

    suspend fun terminarEntrega()

    suspend fun getPaquetesByRoute(routeId: String): List<Paquete>

    suspend fun updatePaqueteStatus(paqueteId: String, nuevoEstado: String)

}