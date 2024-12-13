package com.example.transportistaapp.ui.homeTransportista

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.transportistaapp.R
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.barcode.BarcodeScanning

class RutaEspecificaFragment : Fragment(R.layout.fragment_ruta_especifica) {

    private val scanner: BarcodeScanner = BarcodeScanning.getClient()
    private lateinit var scanButton: Button
    private lateinit var recyclerViewCajas: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias del botón de escanear y RecyclerView
        scanButton = view.findViewById(R.id.btnEscanearCaja)
        recyclerViewCajas = view.findViewById(R.id.recyclerViewCajas)

        // Habilitar o deshabilitar el botón según la lógica de la app
        scanButton.isEnabled = true // Asegúrate de habilitarlo cuando sea necesario

        scanButton.setOnClickListener {
            val bitmap = captureImage() // Este método debes implementarlo según cómo capturas la imagen
            scanImage(bitmap)
        }
    }

    // Método para escanear la imagen utilizando ML Kit
    private fun scanImage(image: Bitmap) {
        val inputImage = InputImage.fromBitmap(image, 0)

        // Procesar la imagen con ML Kit
        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    // obtienes el código escaneado
                    val value = barcode.displayValue
                    Log.d("ScannedBarcode", "Barcode value: $value")

                    // actualizar el RecyclerView o hacer lo que necesites


                    // Para mostrar los datos escaneados
                }
            }
            .addOnFailureListener { e ->
                Log.e("BarcodeScanner", "Error scanning barcode", e)
            }
    }

    private fun captureImage(): Bitmap {
        // Aquí debes capturar la imagen desde la cámara o desde una fuente
        // En este ejemplo, solo retornamos un Bitmap vacío
        return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }
}
