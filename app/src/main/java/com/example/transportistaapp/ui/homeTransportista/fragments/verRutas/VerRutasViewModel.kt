package com.example.transportistaapp.ui.homeTransportista.fragments.verRutas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.useCases.GetRutasActivasUseCase
import com.example.transportistaapp.domain.useCases.ComenzarEntregasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerRutasViewModel  @Inject constructor(
    private val getRutasActivasUseCase: GetRutasActivasUseCase,  // Dependencia del repositorio
    private val comenzarEntregasUseCase : ComenzarEntregasUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<VerRutasState>(VerRutasState.Loading)
    val state: StateFlow<VerRutasState> = _state

    // Obtener las rutas activas desde el repositorio
    fun obtenerRutasActivas() {
        viewModelScope.launch {
            _state.value = VerRutasState.Loading
            try {
                val rutas = getRutasActivasUseCase()
                Log.d("MaybeaLog verRutasVM", rutas.toString())
                _state.value = VerRutasState.Success(rutas)
            } catch (e: Exception) {
                _state.value = VerRutasState.Error(e.toString())
            }
        }
    }

    fun comenzarEntregas() {
        viewModelScope.launch {
            comenzarEntregasUseCase()
        }
    }
}