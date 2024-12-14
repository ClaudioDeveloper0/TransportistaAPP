package com.example.transportistaapp.ui.pantallaReparto.fragments.mapa

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.transportistaapp.R
import com.example.transportistaapp.databinding.FragmentMapaBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport
import kotlinx.coroutines.launch


class MapaFragment : Fragment() {
    private var _binding: FragmentMapaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapaViewModel by viewModels()
    private var mapView: MapView? = null
    private var locationAccess: Boolean = false
    private var permissionsManager: PermissionsManager? = null

    var permissionsListener: PermissionsListener = object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        }

        override fun onPermissionResult(granted: Boolean) {
            if (granted) {
                locationAccess = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PermissionsManager.areLocationPermissionsGranted(requireContext())) {
            locationAccess = true
        } else {
            permissionsManager = PermissionsManager(permissionsListener)
            activity?.let { permissionsManager?.requestLocationPermissions(it) }
        }
        // Obtener el JSON desde los argumentos
        arguments?.getString("coordenadas")?.let { json ->
            val coordenadasType = object : TypeToken<List<List<Double>>>() {}.type
            val coordenadas: List<List<Double>> = Gson().fromJson(json, coordenadasType)
            viewModel.setCoordenadas(coordenadas)  // Pasamos las coordenadas al ViewModel
        }
        arguments?.getDouble("lat")?.let { latitud ->
            val longitud = requireArguments().getDouble("long")
//            viewModel.crearRuta(latitud, longitud)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapaBinding.inflate(layoutInflater, container, false)
        mapView = binding.mapView
        if (locationAccess) enableLocationComponent()
        mapView?.mapboxMap?.let { mapboxMap ->
            mapboxMap.loadStyle(
                Style.MAPBOX_STREETS
            ) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is MapaState.Success -> {
                                addAnnotationsToMap(state.coordenadas)
                            }

                            else -> {
                                // Manejar otros estados como Loading o Error si es necesario
                            }
                        }
                    }
                }
            }
//            viewportDataSource = MapboxNavigationViewportDataSource
        }
        return binding.root
    }

    private fun enableLocationComponent() {
        with(mapView!!) {
            location.locationPuck = createDefault2DPuck(withBearing = true)
            location.enabled = true
            location.puckBearing = PuckBearing.COURSE
            location.puckBearingEnabled = true
            viewport.transitionTo(
                targetState = viewport.makeFollowPuckViewportState(),
                transition = viewport.makeImmediateViewportTransition()
            )
        }
    }

    private fun addAnnotationsToMap(coordenadas: List<List<Double>>) {
        coordenadas.forEach { coord ->
            val point = Point.fromLngLat(coord[0], coord[1])
            bitmapFromDrawableRes(requireContext(), R.drawable.pin_24)?.let {
                val annotationApi = mapView?.annotations
                val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
                val pointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(point)
                    .withIconAnchor(IconAnchor.BOTTOM)
                    .withIconImage(it)
                pointAnnotationManager?.create(pointAnnotationOptions)
            }
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect {
                        when (it) {
                            MapaState.Loading -> loadingState()
                            is MapaState.Success -> successState(it.coordenadas)
                            is MapaState.Error -> errorState(it.error)
                        }
                    }
                }
            }
        }
    }

    private fun errorState(error: String) {
        // TODO("Not yet implemented")
    }

    private fun successState(coordenadas: List<List<Double>>) {
        // TODO("Not yet implemented")
    }

    private fun loadingState() {
        // TODO("Not yet implemented")
    }
}
