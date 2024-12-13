package com.example.transportistaapp.ui.pantallaReparto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.useCases.GetPaquetesByRouteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RouteDetailViewModel @Inject constructor(
    private val getPaquetesByRoute : GetPaquetesByRouteUseCase
) : ViewModel() {


    private val _paquetes = MutableLiveData<List<Paquete>>()
    val paquetes: LiveData<List<Paquete>> get() = _paquetes

    fun fetchBoxes(routeId: String) {
        viewModelScope.launch {
            val boxes = getPaquetesByRoute(routeId)
            _paquetes.value = boxes
        }
    }

    fun validateBox(boxId: String) {
        viewModelScope.launch {
            // Refresh the list after validation
            _paquetes.value = _paquetes.value?.map {
                if (it.id == boxId) it.copy(estado = "cargado") else it
            }
        }
    }
}
