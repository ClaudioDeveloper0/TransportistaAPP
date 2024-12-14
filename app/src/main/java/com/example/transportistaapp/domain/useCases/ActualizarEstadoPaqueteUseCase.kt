package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.Repository
import javax.inject.Inject

class ActualizarEstadoPaqueteUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(paqueteId: String, nuevoEstado: String) {
        repository.updatePaqueteStatus(paqueteId, nuevoEstado)
    }
}
