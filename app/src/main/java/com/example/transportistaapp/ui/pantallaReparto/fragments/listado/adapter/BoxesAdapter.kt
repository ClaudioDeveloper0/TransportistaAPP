package com.example.transportistaapp.ui.pantallaReparto.fragments.listado.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.databinding.ItemBoxBinding
import com.example.transportistaapp.domain.model.Paquete

class BoxesAdapter(
    private val boxes: List<Paquete>,
    private val onValidateClicked: (Paquete) -> Unit
) : RecyclerView.Adapter<BoxesAdapter.BoxViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        val binding = ItemBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoxViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {
        holder.bind(boxes[position])
    }

    override fun getItemCount(): Int = boxes.size

    inner class BoxViewHolder(private val binding: ItemBoxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(box: Paquete) {
            binding.tvPaqueteID.text = box.id
            binding.tvPaqueteEstado.text = if (box.estado == "cargado") "Cargado" else "Pendiente"
            binding.btnValidate.setOnClickListener { onValidateClicked(box) }
        }
    }
}
