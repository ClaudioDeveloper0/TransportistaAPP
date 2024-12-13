package com.example.transportistaapp.ui.listadoCargaEntregas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.useCases.CajaEntregadaUseCase
import com.example.transportistaapp.domain.useCases.GetPaquetesEnRepartoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoCargaEntregasViewModel @Inject constructor(
    private val GetPaquetesEnRepartoUseCase: GetPaquetesEnRepartoUseCase,
    private val cajaEntregadaUseCase: CajaEntregadaUseCase
) : ViewModel() {

    private var _paquetesState =
        MutableStateFlow<ListadoCargaEntregasState>(ListadoCargaEntregasState.Loading)
    val paquetesState: StateFlow<ListadoCargaEntregasState> = _paquetesState

    init {
        obtenerCajas()
    }

    private fun obtenerCajas() {
        viewModelScope.launch {
            _paquetesState.value = ListadoCargaEntregasState.Loading
            try {
                val paquetes = GetPaquetesEnRepartoUseCase()
                _paquetesState.value = ListadoCargaEntregasState.Success(paquetes)
            } catch (e: Exception) {
                ListadoCargaEntregasState.Error("Error: $e")
            }
        }
    }

    fun registrarEntregas(paquetes: List<Paquete>) {
        viewModelScope.launch {
            try {
                paquetes.forEach {
                    cajaEntregadaUseCase(it.id)
                }
            } catch (e: Exception) {
                Log.d("MaybeaLog listadoCargaEntregas 50", "Error: $e")
            }
        }
    }
}
