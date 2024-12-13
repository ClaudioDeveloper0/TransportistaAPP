package com.example.transportistaapp.ui.finalizaciondia

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FinalizacionDiaViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _entregasNoRealizadasLiveData = MutableLiveData<List<Entrega>>()
    val entregasNoRealizadasLiveData: LiveData<List<Entrega>> get() = _entregasNoRealizadasLiveData

    fun obtenerEntregasNoRealizadas() {
        viewModelScope.launch {
            try {
                val entregas = firebaseRepository.obtenerEntregasNoRealizadas()
                _entregasNoRealizadasLiveData.value = entregas
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun finalizarDia() {
        viewModelScope.launch {
            try {
                firebaseRepository.finalizarDia()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}
