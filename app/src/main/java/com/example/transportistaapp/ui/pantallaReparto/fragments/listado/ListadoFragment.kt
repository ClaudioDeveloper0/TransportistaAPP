package com.example.transportistaapp.ui.pantallaReparto.fragments.listado

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transportistaapp.databinding.FragmentListadoBinding
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.ui.homeTransportista.RutasActivity
import com.example.transportistaapp.ui.pantallaReparto.RepartoActivity
import com.example.transportistaapp.ui.pantallaReparto.fragments.listado.adapter.ListadoAdapter
import com.example.transportistaapp.ui.pantallaReparto.fragments.mapa.MapaFragment
import com.google.gson.Gson
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListadoFragment : Fragment() {
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!
    private val listadoViewModel: ListadoViewModel by viewModels()
    private lateinit var listadoAdapter: ListadoAdapter
    private var permissionsListener: PermissionsListener = object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) { }
        override fun onPermissionResult(granted: Boolean) { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!PermissionsManager.areLocationPermissionsGranted(requireContext())) {
            val permissionsManager = PermissionsManager(permissionsListener)
            activity?.let { permissionsManager.requestLocationPermissions(it) }
        }
        _binding = FragmentListadoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }
    override fun onResume() {
        super.onResume()
        listadoAdapter.updateList(emptyList())
        actualizarMovimientos()
    }
    private fun initUI(){
        initPaquetes()
        initUIState()
        initListeners()
    }

    private fun initListeners() {
        binding.btnVerMapa.setOnClickListener {
            val paquetes:List<Paquete> = listadoViewModel.paquetes.value ?: emptyList()
            val coordenadas = paquetes.map { it.coordenadas }
            val jsonCoordenadas = Gson().toJson(coordenadas)
            val mapaFragment = MapaFragment().apply {
                arguments = Bundle().apply {
                    putString("coordenadas", jsonCoordenadas)
                }
            }
            val fragmentContainerId = (requireActivity() as RepartoActivity).binding.fragmentContainer.id
            parentFragmentManager.beginTransaction()
                .replace(fragmentContainerId, mapaFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun initPaquetes() {
        listadoAdapter = ListadoAdapter() { dato ->
            val mapaFragment = MapaFragment().apply {  // aqui cambia MapaFragment por el fragmento a donde quieras redirccionar
                arguments = Bundle().apply {
                    putDouble("lat", dato.coordenadas[0])
                    putDouble("long", dato.coordenadas[1])
                }
            }
            val activityBinding = (requireActivity() as RepartoActivity).binding
            parentFragmentManager.beginTransaction()
                .replace(activityBinding.fragmentContainer.id, mapaFragment) // aquí tienes que meter el fragmento
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerViewCajas.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listadoAdapter
        }
    }
    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    listadoViewModel.state.collect {
                        when (it) {
                            ListadoState.Loading -> loadingState()
                            is ListadoState.Success -> successState(it.paquetes)
                            is ListadoState.Error -> errorState(it.error)
                        }
                    }
                }
            }
        }
    }
    private fun loadingState() {
        //TODO("Hay que poner un spinner o algo de cargando en la pantalla")
    }
    private fun successState(paquetes : List<Paquete>) {
        listadoAdapter.updateList(paquetes)
    }
    private fun errorState(error:String) {
        Log.d("MaybeaLog", error)
    }




    private fun actualizarMovimientos() {
        listadoViewModel.cargarRutas()
    }
}
