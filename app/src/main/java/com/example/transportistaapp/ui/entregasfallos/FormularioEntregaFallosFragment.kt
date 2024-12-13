package com.example.transportistaapp.ui.entregasfallos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.transportistaapp.databinding.FragmentFormularioEntregaFallosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormularioEntregaFallosFragment : Fragment() {

    private var _binding: FragmentFormularioEntregaFallosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FormularioEntregaFallosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormularioEntregaFallosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paqueteID = arguments?.getString("paqueteId") ?: ""
        binding.btnRegistrarFallo.setOnClickListener {
            viewModel.registrarFallo(paqueteID, binding.etMotivoFallo.text.toString())
        }
    }
}
