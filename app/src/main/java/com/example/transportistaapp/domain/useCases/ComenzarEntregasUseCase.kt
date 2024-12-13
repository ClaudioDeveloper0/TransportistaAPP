package com.example.transportistaapp.domain.useCases

import com.example.transportistaapp.domain.Repository
import javax.inject.Inject

class ComenzarEntregasUseCase@Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
        // nombreParametro : String -> nombre de algo
        // nombreParametro2 : List<ClaseFicticia> -> lista de tal cosa
    ) {
        //return TipoDato -> este dato contiene tal y tal cosa
    }
}