package database

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.room.TypeConverter
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
actual fun encodeBitmap(bitmap: ImageBitmap?): String? = bitmap?.let { imageBitmap ->
    Image.makeFromBitmap(imageBitmap.asSkiaBitmap()).encodeToData(EncodedImageFormat.PNG, 100)?.let {
        Base64.Default.encode(it.bytes)
    }
}

@OptIn(ExperimentalEncodingApi::class)
actual fun decodeBitmap(string: String?): ImageBitmap? = string?.let {
    Image.makeFromEncoded(
        Base64.Default.decode(it)
    ).toComposeImageBitmap()
}
