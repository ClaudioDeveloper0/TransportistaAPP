package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import javax.inject.Inject
class GetRutasActivasUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(uid: String): List<Ruta> {
        return repository.getRutasActivas(uid)
    }
    suspend fun getPaquetesByRoute(rutaId: String): List<Paquete> {
        return repository.getPaquetesByRoute(rutaId)
    }
}
