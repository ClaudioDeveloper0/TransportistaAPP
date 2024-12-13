package com.example.transportistaapp.ui.finalizaciondia

import com.example.transportistaapp.domain.model.Paquete

sealed class FinalizacionDiaState {
    data object Loading : FinalizacionDiaState()
    data object Success : FinalizacionDiaState()
    data class Error(val paquetes: List<Paquete>) : FinalizacionDiaState()
}