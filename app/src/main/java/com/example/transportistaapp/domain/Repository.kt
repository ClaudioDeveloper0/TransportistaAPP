package com.example.transportistaapp.domain

import com.google.firebase.auth.FirebaseUser

interface Repository {
    suspend fun loginTransportista(user:String, password:String) : FirebaseUser?
}