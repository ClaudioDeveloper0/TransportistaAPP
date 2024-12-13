package com.example.transportistaapp.ui.pantallaReparto.fragments.listado.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.databinding.ItemPaqueteBinding
import com.example.transportistaapp.domain.model.Paquete

class BoxesAdapter(
    private val boxes: List<Paquete>,
    private val onValidateClicked: (Paquete) -> Unit
) : RecyclerView.Adapter<BoxesAdapter.BoxViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        val binding = ItemPaqueteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoxViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {
        holder.bind(boxes[position])
    }

    override fun getItemCount(): Int = boxes.size

    inner class BoxViewHolder(private val binding: ItemPaqueteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(box: Paquete) {
            binding.tvCodigoPaquete.text = box.id
            binding.tvEstado.text = if (box.estado == "cargado") "Cargado" else "Pendiente"
            binding.tvDireccion.text = box.direccion
        }
    }
}
