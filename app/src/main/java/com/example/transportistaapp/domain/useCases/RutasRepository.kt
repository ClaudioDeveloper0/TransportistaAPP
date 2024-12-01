package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.model.RutaT

interface RutasRepository {
    suspend fun getRutasActivas(uid: String): List<RutaT>
}
