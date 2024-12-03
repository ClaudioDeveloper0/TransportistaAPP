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
import com.example.transportistaapp.ui.pantallaReparto.fragments.listado.adapter.ListadoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListadoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!
    private val listadoViewModel: ListadoViewModel by viewModels()
    private lateinit var listadoAdapter: ListadoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
    }
    private fun initPaquetes() {
        listadoAdapter = ListadoAdapter() { dato ->
//            val mapaFragment = MapaFragment().apply {  // aqui cambia MapaFragment por el fragmento a donde quieras redirccionar
//                arguments = Bundle().apply {
//                    putString("paqueteId", dato.id) //esto son parametros que le puedes pasar
//                }
//            }
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, mapaFragment) // aquí tienes que meter el fragmento
//                .addToBackStack(null)
//                .commit()
        }
        binding.rvPaquetes.apply {
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