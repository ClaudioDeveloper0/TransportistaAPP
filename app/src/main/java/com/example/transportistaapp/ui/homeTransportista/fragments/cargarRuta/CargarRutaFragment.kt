package com.example.transportistaapp.ui.homeTransportista.fragments.cargarRuta

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transportistaapp.databinding.FragmentCargarRutaBinding
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.ui.homeTransportista.fragments.cargarRuta.adapter.CargarRutaAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CargarRutaFragment : Fragment() {
    private var _binding: FragmentCargarRutaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CargarRutaViewModel by viewModels()
    private lateinit var cargarRutaAdapter : CargarRutaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCargarRutaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initAdapter()
        initState()
    }
    override fun onResume() {
        super.onResume()
        cargarRutaAdapter.updateList(emptyList())
        obtenerRutas()
    }

    private fun obtenerRutas() {
        val ruta = arguments?.getString("rutaId") ?: ""
        viewModel.obtenerPaquetes(ruta)
    }
    private fun initState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        CargarRutaState.Loading -> loadingState()
                        is CargarRutaState.Error -> errorState(state.error)
                        is CargarRutaState.Success -> successState(state.rutas)
                    }
                }
            }
        }
    }

    private fun successState(paquetes: List<Paquete>) {
        cargarRutaAdapter.updateList(paquetes)
    }

    private fun errorState(error: String) {
        Log.d("MaybeaLog CargarRutaFragment 63", "Error: $error")
    }

    private fun loadingState() {
//        TODO("Not yet implemented")
    }

    private fun initAdapter() {
        Log.d("MaybeaLog CargarRutaFragment 86", "Init adapter 1")
        cargarRutaAdapter = CargarRutaAdapter()
        binding.recyclerViewCajas.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cargarRutaAdapter
        }
        Log.d("MaybeaLog CargarRutaFragment 86", "Init adapter 2")
    }
}