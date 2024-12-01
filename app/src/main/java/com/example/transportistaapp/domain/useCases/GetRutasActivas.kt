package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.model.RutaT

class GetRutasActivas(private val rutasRepository: RutasRepository) {
    suspend operator fun invoke(uid: String): List<RutaT> {
        return rutasRepository.getRutasActivas(uid)
    }
}
