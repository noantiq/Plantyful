package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject

@Composable
actual fun rememberGalleryManager(onResult: (ImageBitmap?) -> Unit): GalleryManager {
    val imagePicker = UIImagePickerController().apply {
        setAllowsEditing(true)
    }
    val galleryDelegate = remember {
        object : NSObject(),
            UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {

            override fun imagePickerController(
                picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val image =
                    didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerEditedImage) as? UIImage
                        ?: didFinishPickingMediaWithInfo.getValue(
                            UIImagePickerControllerOriginalImage
                        ) as? UIImage
                // TODO: convert to ImageBitmap
                onResult(null)
                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }
    return remember {
        GalleryManager {
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                imagePicker.apply { setDelegate(galleryDelegate) }, true, null
            )
        }
    }
}

actual class GalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() = onLaunch()
}

actual fun ImageBitmap.crop(
    x: Int,
    y: Int,
    width: Int,
    height: Int
): ImageBitmap {
    //TODO
    return this
}