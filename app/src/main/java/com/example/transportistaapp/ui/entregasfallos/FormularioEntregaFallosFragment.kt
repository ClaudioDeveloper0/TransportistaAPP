package com.example.transportistaapp.ui.entregasfallos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormularioEntregaFallosFragment : Fragment(R.layout.fragment_formulario_entrega_fallos) {

    private lateinit var binding: FragmentFormularioEntregaFallosBinding
    private val viewModel: FormularioEntregaFallosViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFormularioEntregaFallosBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@FormularioEntregaFallosFragment.viewModel
        }

        binding.btnRegistrarEntrega.setOnClickListener {
            val entrega = Entrega(
                nombre = binding.etNombre.text.toString(),
                rut = binding.etRUT.text.toString(),
                telefono = binding.etTelefono.text.toString()
            )
            viewModel.registrarEntrega(entrega)
        }

        binding.btnRegistrarFallo.setOnClickListener {
            val fallo = Fallo(
                motivo = binding.etMotivoFallo.text.toString()
            )
            viewModel.registrarFallo(fallo)
        }
    }
}
