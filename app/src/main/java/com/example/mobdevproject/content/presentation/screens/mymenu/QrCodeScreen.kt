package com.example.mobdevproject.content.presentation.screens.mymenu

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.mobdevproject.content.domain.blogic.CreateQrCode
import com.example.mobdevproject.content.domain.blogic.FileSystemFunctions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCodeScreen(
    modifier: Modifier = Modifier,
    menuId: String,
    context: Context,
    goBack:() -> Unit,
) {
    val qrCodeBitmap = CreateQrCode().generateQRCode(
        text = menuId,
        width = 800,
        height = 800,
    )

    val documentUriLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocumentTree()) { uri ->
            uri?.let {
                qrCodeBitmap?.let {
                    FileSystemFunctions().saveBitmapIntoNewFile(context, uri, qrCodeBitmap)
                }
            }
        }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = modifier.padding(top = 12.dp),
                title = {
                    Text(text = "QR code")
                },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Share qr code")
                    }
                },
            )
        }
    ) { paddingValues: PaddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (qrCodeBitmap != null) {
                Text(text = "This QR code uses to find your menu")
                Spacer(modifier = modifier.height(20.dp))
                Image(
                    bitmap = qrCodeBitmap,
                    contentDescription = "QR code image",
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = modifier.height(24.dp))
                Button(onClick = { documentUriLaunch.launch(null) }) {
                    Text(text = "Save this QR code as .JPEG file")
                }
            } else
                Text(text = "There is no QR code")
        }
    }
}