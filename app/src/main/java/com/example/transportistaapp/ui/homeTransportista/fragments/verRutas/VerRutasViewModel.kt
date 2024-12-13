package com.example.transportistaapp.ui.homeTransportista.fragments.verRutas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.useCases.GetRutasActivasUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerRutasViewModel  @Inject constructor(
    private val getRutasActivasUseCase: GetRutasActivasUseCase,  // Dependencia del repositorio
    private val auth : FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow<VerRutasState>(VerRutasState.Loading)
    val state: StateFlow<VerRutasState> = _state

    // Obtener las rutas activas desde el repositorio
    fun obtenerRutasActivas() {
        viewModelScope.launch {
            _state.value = VerRutasState.Loading
            try {
                val rutas = getRutasActivasUseCase(auth.currentUser!!.uid)
                _state.value = VerRutasState.Success(rutas)
            } catch (e: Exception) {
                _state.value = VerRutasState.Error(e.toString())
            }
        }
    }
}