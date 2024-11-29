package com.example.transportistaapp.data


import com.google.firebase.auth.FirebaseAuthException

object FirebaseErrorUtils {
    fun getErrorMessage(exception: Exception): String {
        return when (exception) {
            is FirebaseAuthException -> {
                when (exception.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "El correo no tiene un formato válido."
                    "ERROR_USER_NOT_FOUND" -> "Usuario no encontrado."
                    "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta."
                    else -> "Error de autenticación: ${exception.errorCode}"
                }
            }
            else -> "Error desconocido: ${exception.localizedMessage}"
        }
    }
}
