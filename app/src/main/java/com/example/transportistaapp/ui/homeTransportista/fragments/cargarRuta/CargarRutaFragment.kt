package com.example.transportistaapp.ui.homeTransportista.fragments.cargarRuta

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.transportistaapp.R
import com.example.transportistaapp.databinding.FragmentCargarRutaBinding
import com.example.transportistaapp.databinding.FragmentListadoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CargarRutaFragment : Fragment() {
    private var _binding: FragmentCargarRutaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CargarRutaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCargarRutaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}