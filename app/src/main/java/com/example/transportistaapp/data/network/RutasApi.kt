package com.example.transportistaapp.data.network

import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body

interface RutasApi {

    @GET("rutas/activas/{uid}")
    suspend fun getRutasActivas(@Path("uid") uid: String): List<Ruta>

    @GET("paquetes/ruta/{routeId}")
    suspend fun getPaquetesByRoute(@Path("routeId") routeId: String): List<Paquete>

    @POST("paquete/status")
    suspend fun updatePaqueteStatus(@Body paqueteStatus: Paquete)
}
