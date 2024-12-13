package com.example.transportistaapp.ui.listadoCargaEntregas.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.databinding.ItemBoxBinding
import com.example.transportistaapp.domain.model.Paquete

class ListadoCargaEntregasViewHolder(view: View, private val context: Context) :
    RecyclerView.ViewHolder(view) {

    private val binding = ItemBoxBinding.bind(view)
//        private val parent = binding.parent

    fun render(paquete: Paquete) {
        binding.tvPaqueteID.text = paquete.id // Enlazar el objeto Caja con el layout
    }
}