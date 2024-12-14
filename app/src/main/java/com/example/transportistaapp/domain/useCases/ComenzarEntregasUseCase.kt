package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Ruta
import javax.inject.Inject
import kotlin.collections.forEach

class ComenzarEntregasUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(rutas: List<Ruta>) {
        rutas.forEach { ruta ->
            // Modificar la ruta para marcarla como "En entrega"
            val rutaActualizada = ruta.copy(enReparto = true)

            // Actualizar la ruta en el repositorio
            repository.actualizarRuta(rutaActualizada)
        }
    }
}
