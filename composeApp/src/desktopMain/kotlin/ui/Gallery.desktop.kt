package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.graphics.toComposeImageBitmap

@Composable
actual fun rememberGalleryManager(onResult: (ImageBitmap?) -> Unit): GalleryManager {
    return GalleryManager {  }
    //TODO("Not yet implemented")
}

actual class GalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() = onLaunch()
}

actual fun ImageBitmap.crop(x: Int, y: Int, width: Int, height: Int): ImageBitmap =
    toAwtImage().getSubimage(x, y, width, height).toComposeImageBitmap()