package com.example.transportistaapp.ui.pantallaReparto.fragments.mapa

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
import com.example.transportistaapp.databinding.FragmentMapaBinding
import com.mapbox.navigation.core.directions.session.RoutesObserver
import com.mapbox.navigation.ui.maps.camera.data.MapboxNavigationViewportDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapaFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var _binding: FragmentMapaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewportDataSource :  MapboxNavigationViewportDataSource
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val paqueteId = arguments?.getString("paqueteId")
        Log.d("MaybeaLogMapaFragment", paqueteId.orEmpty())
        viewportDataSource = MapboxNavigationViewportDataSource(binding.mapView.mapboxMap)
        initUI()

    }

    private fun initUI() {
        initRoute()
    }

    private fun initRoute() {
        val routesObserver = RoutesObserver { routes ->
            if (routes.navigationRoutes.isNotEmpty()) {
                viewportDataSource.onRouteChanged(routes.navigationRoutes.first())
                viewportDataSource.evaluate()
            } else {
                viewportDataSource.clearRouteData()
                viewportDataSource.evaluate()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }


}