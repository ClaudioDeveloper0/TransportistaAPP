package com.example.transportistaapp.ui.cargaentregas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoCargaEntregasViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _cajasLiveData = MutableLiveData<List<Caja>>()
    val cajasLiveData: LiveData<List<Caja>> get() = _cajasLiveData

    init {
        obtenerCajas()
    }

    private fun obtenerCajas() {
        viewModelScope.launch {
            try {
                val cajas = firebaseRepository.obtenerCajas()
                _cajasLiveData.value = cajas
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun registrarEntregas(cajas: List<Caja>) {
        viewModelScope.launch {
            try {
                firebaseRepository.registrarEntregas(cajas)
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}
