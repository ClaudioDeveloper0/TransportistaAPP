package com.example.transportistaapp.domain.model

data class Ruta(
    val id: String,
    val nombre: String,
    val cargado: Boolean,
    val validado: Boolean,
    var paquetes : List<Paquete> = emptyList()
)
