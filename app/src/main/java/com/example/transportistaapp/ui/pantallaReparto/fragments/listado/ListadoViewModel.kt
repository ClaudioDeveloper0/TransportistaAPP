package com.example.transportistaapp.ui.pantallaReparto.fragments.listado

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.useCases.GetRutasActivasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListadoViewModel@Inject constructor(
    private var getRutasActivasUseCase: GetRutasActivasUseCase,
) : ViewModel() {

    private val _paquetes = MutableLiveData<List<Paquete>>()
    val paquetes: LiveData<List<Paquete>> = _paquetes
    private var _state = MutableStateFlow<ListadoState>(ListadoState.Loading)
    val state: StateFlow<ListadoState> = _state



    fun cargarRutas() {
        viewModelScope.launch {
            _state.value = ListadoState.Loading
            val rutas = withContext(Dispatchers.IO) { getRutasActivasUseCase() }
            val paquetes = mutableListOf<Paquete>()
            rutas.forEach {
                paquetes.addAll(it.paquetes)
            }
            if (paquetes.isEmpty()) {
                _state.value =
                    ListadoState.Error("Ha ocurrido un error, getStockUseCase() -> null")
            } else {
                _paquetes.value = paquetes
                _state.value = ListadoState.Success(paquetes = paquetes)
            }

        }
    }
}
