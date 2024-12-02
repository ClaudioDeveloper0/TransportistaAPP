package com.example.transportistaapp.ui.homeTransportista.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.R
import com.example.transportistaapp.databinding.ItemRutaBinding
import com.example.transportistaapp.domain.model.Ruta

class RutasViewHolder(view: View, private val context: Context) :
    RecyclerView.ViewHolder(view) {

    private val binding = ItemRutaBinding.bind(view)
    private val parent = binding.parent

    fun render(ruta : Ruta){
        binding.tvRutaNombre.text = ruta.nombre
        binding.tvRutaEstado.text = if (ruta.completado || ruta.cargado) "Cargada" else "Pendiente"
        binding.tvCantidadPaquetes.text =
            context.getString(R.string.x_paquetes, ruta.paquetes.count().toString())
        parent.setBackgroundColor(
            if (ruta.completado || ruta.cargado) Color.GREEN else Color.LTGRAY
        )
    }

}