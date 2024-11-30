package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.Repository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginTransportista @Inject constructor(private val repository : Repository) {
    suspend operator fun invoke(user:String, password:String) : FirebaseUser? {
         return repository.loginTransportista(user, password)
    }
}