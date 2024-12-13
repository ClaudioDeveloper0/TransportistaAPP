package com.example.transportistaapp.ui.listadoCargaEntregas.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.R
import com.example.transportistaapp.domain.model.Paquete

class ListadoCargaEntregasAdapter(private var paquetes: List<Paquete> = emptyList()) :
    RecyclerView.Adapter<ListadoCargaEntregasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListadoCargaEntregasViewHolder {
        return ListadoCargaEntregasViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_box, parent, false), parent.context
        )
    }

    override fun onBindViewHolder(holder: ListadoCargaEntregasViewHolder, position: Int) {
        val paquete = paquetes[position]
        holder.render(paquete)
    }

    override fun getItemCount() = paquetes.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Paquete>) {
        paquetes = list
        notifyDataSetChanged()
    }
    fun getSelectedCajas() :List<Paquete> {
        return emptyList()
    }
}
