package com.example.transportistaapp.ui.homeTransportista

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.R
import com.example.transportistaapp.domain.model.RutaT

class RutasAdapter : RecyclerView.Adapter<RutasAdapter.RutaViewHolder>() {

    private val rutasList = mutableListOf<RutaT>()

    // Clase interna para representar cada elemento visual
    class RutaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rutaNombre: TextView = view.findViewById(R.id.tvRutaNombre)
        val rutaEstado: TextView = view.findViewById(R.id.tvRutaEstado)
    }

    // Crea el diseño de cada fila
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ruta, parent, false) // Asegúrate de que este archivo existe y está en el lugar correcto
        return RutaViewHolder(view)
    }


    // Vincula los datos de la lista a cada fila
    override fun onBindViewHolder(holder: RutaViewHolder, position: Int) {
        val ruta = rutasList[position]
        holder.rutaNombre.text = ruta.nombre
        holder.rutaEstado.text = if (ruta.validado || ruta.cargado) "Cargada" else "Pendiente"
        holder.itemView.setBackgroundColor(
            if (ruta.validado || ruta.cargado) Color.GREEN else Color.LTGRAY
        )
    }

    // Cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return rutasList.size
    }

    // c actualizan la lista de rutas
    fun submitList(rutas: List<RutaT>) {
        rutasList.clear()
        rutasList.addAll(rutas)
        notifyDataSetChanged()
    }
}
