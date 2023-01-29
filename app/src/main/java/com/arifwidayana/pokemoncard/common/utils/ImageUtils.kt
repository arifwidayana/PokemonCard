package com.arifwidayana.pokemoncard.common.utils

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.*
import java.net.URL

object ImageUtils {
    suspend fun getImageFromUrl(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection()
                connection.doInput = true
                connection.connect()
                val input = connection.getInputStream()
                BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun downloadAndCompressImage(context: Context, url: String) {
        val downloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Image Download")
            .setDescription("Downloading image...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "${System.currentTimeMillis()}.jpg"
            )
        downloadManager.enqueue(request)
    }
}