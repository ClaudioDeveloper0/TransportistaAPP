package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Paquete
import javax.inject.Inject

class TerminarEntregaUseCase @Inject constructor(private val repository: Repository) {
    /**
     * Verifica si hay paquetes<Paquete> que no se hayan entregado, y en ese caso los devuelve.
     * Si todos los paquetes fueron entregados, guarda los cambios en firebase y retorna null
     */
    suspend operator fun invoke() : List<Paquete> {
        val paquetes = repository.obtenerPaquetesNoEntregados()
        if (paquetes.isEmpty()) {
            repository.terminarEntrega()
        }
        return paquetes
    }
}