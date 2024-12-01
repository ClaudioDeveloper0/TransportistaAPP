package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.RutaT
import javax.inject.Inject

class GetRutasActivas @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(uid: String): List<RutaT> {
        return repository.getRutasActivas(uid)
    }
}
