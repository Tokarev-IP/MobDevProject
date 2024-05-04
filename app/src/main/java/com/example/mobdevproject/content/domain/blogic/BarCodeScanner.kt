package com.example.mobdevproject.content.domain.blogic

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class BarCodeScanner(context: Context) {

    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
        )
        .enableAutoZoom()
        .build()

    private val scanner = GmsBarcodeScanning.getClient(context, options)

    fun startScan(
        onSuccess: (value: String?) -> Unit,
        onFailure: (e: Exception) -> Unit,
    ) {
        scanner.startScan()
            .addOnSuccessListener { barcode: Barcode ->
                val rawValue: String? = barcode.rawValue
                onSuccess(rawValue)
            }
            .addOnCanceledListener {

            }
            .addOnFailureListener { e: Exception ->
                onFailure(e)
            }
    }
}