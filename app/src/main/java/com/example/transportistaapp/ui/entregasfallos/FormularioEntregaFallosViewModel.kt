package com.example.transportistaapp.ui.entregasfallos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormularioEntregaFallosViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _entregaLiveData = MutableLiveData<Resource<Entrega>>()
    val entregaLiveData: LiveData<Resource<Entrega>> get() = _entregaLiveData

    private val _falloLiveData = MutableLiveData<Resource<Fallo>>()
    val falloLiveData: LiveData<Resource<Fallo>> get() = _falloLiveData

    fun registrarEntrega(entrega: Entrega) {
        _entregaLiveData.value = Resource.Loading()
        viewModelScope.launch {
            try {
                firebaseRepository.registrarEntrega(entrega)
                _entregaLiveData.value = Resource.Success(entrega)
            } catch (e: Exception) {
                _entregaLiveData.value = Resource.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun registrarFallo(fallo: Fallo) {
        _falloLiveData.value = Resource.Loading()
        viewModelScope.launch {
            try {
                firebaseRepository.registrarFallo(fallo)
                _falloLiveData.value = Resource.Success(fallo)
            } catch (e: Exception) {
                _falloLiveData.value = Resource.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
