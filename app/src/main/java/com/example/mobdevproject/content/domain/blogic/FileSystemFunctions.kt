package com.example.mobdevproject.content.domain.blogic

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.documentfile.provider.DocumentFile

class FileSystemFunctions {

    fun saveBitmapIntoNewFile(
        context: Context,
        folderUri: Uri,
        imageBitmap: ImageBitmap,
        fileName: String = "myMenuQrCode"
    ) {
        val folder = DocumentFile.fromTreeUri(context, folderUri)
        val file = folder?.createFile("image/jpeg", fileName)
        val bitmap = imageBitmap.asAndroidBitmap()
        file?.uri?.let { fileUri ->
            context.contentResolver.openOutputStream(fileUri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        }
    }
}