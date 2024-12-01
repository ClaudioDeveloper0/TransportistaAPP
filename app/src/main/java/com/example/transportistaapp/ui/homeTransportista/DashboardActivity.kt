package com.example.transportistaapp.ui.homeTransportista

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.R


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var viewModel: RoutesViewModel
    private lateinit var rutasAdapter: RutasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewModel = ViewModelProvider(this)[RoutesViewModel::class.java]
        rutasAdapter = RutasAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRutas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = rutasAdapter

        val btnIniciarEntregas = findViewById<Button>(R.id.btnIniciarEntregas)

        // Observar las rutas activas
        viewModel.rutas.observe(this) { rutas ->
            rutasAdapter.submitList(rutas)
            btnIniciarEntregas.isEnabled = rutas.any { it.validado || it.cargado }
        }

        // Cargar rutas activas (usa el UID correcto)
        viewModel.cargarRutas("UID_TRANSPORTISTA")
    }
}
