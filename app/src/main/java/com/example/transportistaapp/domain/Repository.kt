package com.example.transportistaapp.domain

import com.example.transportistaapp.domain.model.Ruta
import com.google.firebase.auth.FirebaseUser

interface Repository {
    suspend fun loginTransportista(user:String, password:String) : FirebaseUser?

    suspend fun getRutasActivas(uid: String): List<Ruta>
}