package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun rememberGalleryManager(onResult: (ImageBitmap?) -> Unit): GalleryManager

expect fun ImageBitmap.crop(x: Int, y: Int, width: Int, height: Int): ImageBitmap

expect class GalleryManager(onLaunch: () -> Unit) {
    fun launch()
}