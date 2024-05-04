package com.example.mobdevproject.content.domain.blogic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream

class CompressPicture {
    fun compressImage(context: Context, imageUri: Uri, maxWidth: Int, maxHeight: Int, quality: Int): ByteArray {
        val imageStream: InputStream? = context.contentResolver.openInputStream(imageUri)

        val bitmap = BitmapFactory.decodeStream(imageStream)

        var width = bitmap.width
        var height = bitmap.height
        val aspectRatio: Float = width.toFloat() / height.toFloat()

        if (width > maxWidth || height > maxHeight) {
            if (aspectRatio > 1) {
                width = maxWidth
                height = (width / aspectRatio).toInt()
            } else {
                height = maxHeight
                width = (height * aspectRatio).toInt()
            }
        }

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        return outputStream.toByteArray()
    }

}