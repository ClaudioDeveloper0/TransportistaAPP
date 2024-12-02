package com.example.transportistaapp.ui.homeTransportista.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.R
import com.example.transportistaapp.domain.model.Ruta

class RutasAdapter(private var rutas:List<Ruta> = emptyList()) :
    RecyclerView.Adapter<RutasViewHolder>() {

//    private val rutasList = mutableListOf<Ruta>()

    // Clase interna para representar cada elemento visual
//    class RutaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val rutaNombre: TextView = view.findViewById(R.id.tvRutaNombre)
//        val rutaEstado: TextView = view.findViewById(R.id.tvRutaEstado)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutasViewHolder {
        return RutasViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ruta, parent, false), parent.context
        )
    }

    // Vincula los datos de la lista a cada fila
    override fun onBindViewHolder(holder: RutasViewHolder, position: Int) {
        val ruta = rutas[position]
        holder.render(ruta)

    }

    // Cantidad de elementos en la lista
    override fun getItemCount(): Int = rutas.size

    // c actualizan la lista de rutas
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Ruta>) {
        rutas = list
        notifyDataSetChanged()
    }
}
