package com.example.transportistaapp.domain.model


data class Ruta(
    val id: String,
    val nombre: String,                         // El alias de la ruta
    var cargado: Boolean,                       // Si ya está escaneada y cargada
    var enReparto : Boolean,                    // Solo se puede poner en true cuando está cargada
    var completado: Boolean,            // Cuando ya se entregan todos los paquetes, o se termina la ruta, esto cambia a true
    var paquetes : List<Paquete> = emptyList()
)
