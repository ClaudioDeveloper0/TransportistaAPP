package com.example.transportistaapp.domain.useCases

import android.util.Log
import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import javax.inject.Inject

class GetPaquetesByRouteUseCase  @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(routeId:String): List<Paquete> {
        return repository.getPaquetesByRoute(routeId)
    }
}

