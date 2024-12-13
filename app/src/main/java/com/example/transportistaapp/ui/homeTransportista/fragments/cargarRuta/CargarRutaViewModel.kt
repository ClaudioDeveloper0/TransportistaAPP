package com.example.transportistaapp.ui.homeTransportista.fragments.cargarRuta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.useCases.GetPaquetesByRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CargarRutaViewModel @Inject constructor(
    private val getPaquetesByRouteUseCase: GetPaquetesByRouteUseCase,  // Dependencia del repositorio
): ViewModel() {
    private val _state = MutableStateFlow<CargarRutaState>(CargarRutaState.Loading)
    val state: StateFlow<CargarRutaState> = _state
    private val _paquetes= MutableStateFlow<List<Paquete>>(emptyList())
    val paquetes: StateFlow<List<Paquete>> = _paquetes


    fun obtenerPaquetes(rutaID : String) {

        viewModelScope.launch {
            _state.value = CargarRutaState.Loading
            try {
                _paquetes.value = getPaquetesByRouteUseCase(rutaID)
                _state.value = CargarRutaState.Success(_paquetes.value)
            } catch (e: Exception) {
                _state.value = CargarRutaState.Error(e.toString())
            }
        }
    }
}