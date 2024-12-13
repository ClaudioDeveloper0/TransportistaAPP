package com.example.transportistaapp.ui.cargaentregas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CajaAdapter : RecyclerView.Adapter<CajaAdapter.CajaViewHolder>() {

    private var cajaList = listOf<Caja>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CajaViewHolder {
        val binding = ItemCajaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CajaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CajaViewHolder, position: Int) {
        val caja = cajaList[position]
        holder.bind(caja)
    }

    override fun getItemCount() = cajaList.size

    fun submitList(list: List<Caja>) {
        cajaList = list
        notifyDataSetChanged()
    }

    class CajaViewHolder(private val binding: ItemCajaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(caja: Caja) {
            binding.caja = caja // Enlazar el objeto Caja con el layout
            binding.executePendingBindings() // Ejecutar el binding inmediatamente
        }
    }
}
