package com.example.transportistaapp.ui.homeTransportista.fragments.verRutas.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.R
import com.example.transportistaapp.databinding.ItemRutaBinding
import com.example.transportistaapp.domain.model.Ruta

class VerRutasViewHolder(
    view: View,
    private val context: Context,
    private val onRouteClick: (Ruta) -> Unit
) :
    RecyclerView.ViewHolder(view) {

    private val binding = ItemRutaBinding.bind(view)
    private val parent = binding.parent

    fun render(ruta: Ruta) {
        binding.tvRutaNombre.text = ruta.nombre
        binding.tvRutaEstado.text = if (ruta.completado || ruta.cargado) "Cargada" else "Pendiente"
        binding.tvCantidadPaquetes.text =
            context.getString(R.string.x_paquetes, ruta.paquetes.count().toString())
        if (ruta.completado || ruta.cargado) {
            parent.setBackgroundColor(Color.GREEN)
        } else {
            parent.setBackgroundColor(Color.LTGRAY)
            parent.setOnClickListener {
                onRouteClick(ruta) // Llamar al callback con la ruta seleccionada
            }
        }
    }

}