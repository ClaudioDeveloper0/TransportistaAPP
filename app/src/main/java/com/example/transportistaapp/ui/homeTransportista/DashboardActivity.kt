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

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModel: RoutesViewModel
    private lateinit var rutasAdapter: RutasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos el ViewModel y el adapter
        viewModel = ViewModelProvider(this)[RoutesViewModel::class.java]
        rutasAdapter = RutasAdapter()

        // Configuración del RecyclerView usando ViewBinding
        binding.recyclerViewRutas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRutas.adapter = rutasAdapter

        // Configuración del botón usando ViewBinding
        val btnIniciarEntregas = binding.btnIniciarEntregas

        // Observar las rutas activas
        viewModel.rutas.observe(this) { rutas ->
            if (rutas.any { it.enReparto }) {
                val intent = Intent(this, RepartoActivity::class.java) // Reemplaza `NuevaActivity` con el nombre de tu actividad destino
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
            rutasAdapter.submitList(rutas)
            btnIniciarEntregas.isEnabled = rutas.any { it.completado || it.cargado }
        }

        // Cargar rutas activas
        viewModel.cargarRutas()
    }
}
