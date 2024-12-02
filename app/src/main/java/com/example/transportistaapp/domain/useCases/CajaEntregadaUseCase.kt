package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.Repository
import java.util.Date
import javax.inject.Inject

class CajaEntregadaUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(cajaId: String) {
        return repository.marcarCajaEntregada(cajaId, Date())
    }
}
