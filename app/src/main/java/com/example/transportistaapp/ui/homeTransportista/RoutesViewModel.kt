package com.example.transportistaapp.ui.homeTransportista

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.domain.model.Ruta
import com.example.transportistaapp.domain.useCases.GetRutasUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    private val getRutasUseCase: GetRutasUseCase,
    private var auth: FirebaseAuth
) : ViewModel() {

    private val _rutas = MutableLiveData<List<Ruta>>()
    val rutas: LiveData<List<Ruta>> = _rutas



    fun cargarRutas() {
        viewModelScope.launch {
            _rutas.value = getRutasUseCase(auth.currentUser!!.uid)
        }
    }
}
