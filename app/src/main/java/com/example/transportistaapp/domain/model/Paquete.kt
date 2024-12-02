package com.example.transportistaapp.domain.model

data class Paquete (
    val id : String,
    val contacto : String,
    val direccion : String,
    val receptor : String,
    val ruta : String,
    val estado : String,
)