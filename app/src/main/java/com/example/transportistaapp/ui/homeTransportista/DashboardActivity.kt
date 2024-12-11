package com.example.transportistaapp.ui.homeTransportista

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transportistaapp.databinding.ActivityDashboardBinding
import com.example.transportistaapp.ui.homeTransportista.adapter.RutasAdapter
import com.example.transportistaapp.ui.pantallaReparto.RepartoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.Observer

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModel: RoutesViewModel
    private lateinit var rutasAdapter: RutasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(RoutesViewModel::class.java)

        // Configurar RecyclerView
        rutasAdapter = RutasAdapter()
        binding.recyclerViewRutas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRutas.adapter = rutasAdapter

        // Observar las rutas activas
        viewModel.rutas.observe(this, Observer { rutas ->
            // Comprobar si alguna ruta está en reparto
            if (rutas.any { it.enReparto }) {
                // Si alguna ruta está en reparto, iniciar la actividad de reparto
                val intent = Intent(this, RepartoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }

            // Actualizar la lista de rutas en el RecyclerView
            rutasAdapter.submitList(rutas)

            // Habilitar o deshabilitar el botón "Iniciar entregas" según las rutas
            binding.btnIniciarEntregas.isEnabled = rutas.any { it.completado || it.cargado }
        })

        // Configurar el botón "Iniciar entregas"
        binding.btnIniciarEntregas.setOnClickListener {
            val intent = Intent(this, RepartoActivity::class.java)
            startActivity(intent)
        }

        // Cargar las rutas activas
        val transportistaUid = "senWZnLWE0gtQj6HAqgycbuNq4e2"  // Suponiendo que tienes el UID del transportista
        viewModel.obtenerRutasActivas(transportistaUid)
    }
}
