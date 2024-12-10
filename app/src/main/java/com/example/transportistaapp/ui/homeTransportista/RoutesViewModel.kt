package com.example.transportistaapp.ui.homeTransportista

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.data.network.RutasRepositoryImpl
import com.example.transportistaapp.domain.model.Ruta
import com.example.transportistaapp.domain.model.Paquete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoutesViewModel(
    private val rutasRepository: RutasRepositoryImpl  // Dependencia del repositorio
) : ViewModel() {

    // LiveData para observar las rutas
    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas: StateFlow<List<Ruta>> get() = _rutas

    // LiveData para observar el estado de los paquetes
    private val _paquetes = MutableStateFlow<List<Paquete>>(emptyList())
    val paquetes: StateFlow<List<Paquete>> get() = _paquetes

    // LiveData para manejar el estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Obtener las rutas activas desde el repositorio
    fun obtenerRutasActivas(uid: String) {
        _isLoading.value = true  // Activar el loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val rutasActivas = rutasRepository.getRutasActivas(uid)
                _rutas.value = rutasActivas  // Actualizar el estado con las rutas obtenidas
            } catch (e: Exception) {
                // Manejo de errores, puedes agregar un estado de error si lo necesitas
            } finally {
                _isLoading.value = false  // Desactivar el loading
            }
        }
    }

    // Obtener los paquetes de una ruta
    fun obtenerPaquetesPorRuta(routeId: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val paquetesPorRuta = rutasRepository.getPaquetesByRoute(routeId)
                _paquetes.value = paquetesPorRuta  // Actualizar el estado con los paquetes obtenidos
            } catch (e: Exception) {
                // Manejo de errores
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Actualizar el estado de un paquete
    fun actualizarEstadoPaquete(paqueteId: String, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                rutasRepository.updatePaqueteStatus(paqueteId, status)
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}
