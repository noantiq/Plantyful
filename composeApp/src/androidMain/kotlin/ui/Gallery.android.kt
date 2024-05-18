package ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream

@Composable
actual fun rememberGalleryManager(onResult: (ImageBitmap?) -> Unit): GalleryManager {
    val contentResolver = LocalContext.current.contentResolver
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            contentResolver.openInputStream(uri)?.apply {
                readBytes().let {
                    onResult(
                        BitmapFactory.decodeByteArray(it, 0, it.size)
                            .asImageBitmap()
                    )
                }
                close()
            }
        }
    }

    return remember {
        GalleryManager {
            galleryLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
    }
}

actual class GalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() = onLaunch()
}

actual fun ImageBitmap.crop(x: Int, y: Int, width: Int, height: Int): ImageBitmap {
    val outputStream = ByteArrayOutputStream()
    Bitmap.createBitmap(asAndroidBitmap(), x, y, width, height)
        .compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return BitmapFactory.decodeByteArray(
        outputStream.toByteArray(),
        0,
        outputStream.toByteArray().size
    ).asImageBitmap()
}