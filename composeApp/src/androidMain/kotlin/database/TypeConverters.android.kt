package database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
actual fun encodeBitmap(bitmap: ImageBitmap?): String? = bitmap?.let { imageBitmap ->
    val outputStream = ByteArrayOutputStream()
    imageBitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    Base64.Default.encode(outputStream.toByteArray())
}

@OptIn(ExperimentalEncodingApi::class)
actual fun decodeBitmap(string: String?): ImageBitmap? = string?.let {
    val bytes = Base64.Default.decode(it)
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
}