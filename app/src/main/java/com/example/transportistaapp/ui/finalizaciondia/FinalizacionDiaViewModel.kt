package com.example.transportistaapp.ui.finalizaciondia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.useCases.TerminarEntregaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinalizacionDiaViewModel @Inject constructor(
    private val terminarEntregaUseCase: TerminarEntregaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FinalizacionDiaState>(FinalizacionDiaState.Loading)
    val state: StateFlow<FinalizacionDiaState> get() = _state

    fun finalizarDia() {
        viewModelScope.launch {
            FinalizacionDiaState.Loading
            val pendientes = terminarEntregaUseCase()
            if( pendientes.isNotEmpty()){
                FinalizacionDiaState.Error(pendientes)
            } else {
                FinalizacionDiaState.Success
            }
        }
    }
}
