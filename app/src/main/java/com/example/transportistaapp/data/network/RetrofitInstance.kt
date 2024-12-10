package com.example.transportistaapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://tuservidor.com/api/" // Cambia por la URL base de tu API

    val api: RutasApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convertir JSON a objetos
            .build()
            .create(RutasApi::class.java) // Crear la instancia de la API
    }
}
