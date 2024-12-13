package com.example.transportistaapp.domain.useCases

import android.util.Log
import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import javax.inject.Inject
class GetRutasActivasUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(uid: String): List<Ruta> {
        Log.d("maybealog GetRutasActivasUseCase 44", uid)
        return repository.getRutasActivas(uid)
    }
}
