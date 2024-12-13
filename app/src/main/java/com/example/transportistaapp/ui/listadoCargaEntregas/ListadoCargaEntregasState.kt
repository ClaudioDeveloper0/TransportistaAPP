package com.example.transportistaapp.ui.listadoCargaEntregas

import com.example.transportistaapp.domain.model.Paquete

sealed class ListadoCargaEntregasState {
    data object Loading : ListadoCargaEntregasState()
    data class Error(val error:String) : ListadoCargaEntregasState()
    data class Success(val paquetes: List<Paquete>) : ListadoCargaEntregasState()
}