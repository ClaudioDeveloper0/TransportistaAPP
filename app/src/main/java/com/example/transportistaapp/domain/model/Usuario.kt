package com.example.transportistaapp.domain.model

import com.google.firebase.auth.FirebaseUser

data class Usuario(
    val uid : String,
    val correo:String,
    val telefono:String,
    val tipo:Int,
    val rut:String
)


