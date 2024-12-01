package com.example.transportistaapp.ui.homeTransportista

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.model.RutaT
import com.example.transportistaapp.domain.useCases.GetRutasActivas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    private val getRutasActivas: GetRutasActivas
) : ViewModel() {

    private val _rutas = MutableLiveData<List<RutaT>>()
    val rutas: LiveData<List<RutaT>> = _rutas

    fun cargarRutas(uid: String) {
        viewModelScope.launch {
            _rutas.value = getRutasActivas(uid)
        }
    }
}
