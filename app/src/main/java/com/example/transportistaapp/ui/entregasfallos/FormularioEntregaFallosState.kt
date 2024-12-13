package com.example.transportistaapp.ui.entregasfallos


sealed class FormularioEntregaFallosState {
    data object Loading : FormularioEntregaFallosState()
    data object Error : FormularioEntregaFallosState()
    data object Success : FormularioEntregaFallosState()
}