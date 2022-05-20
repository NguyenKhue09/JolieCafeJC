package com.khue.joliecafejp.presentation.components

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.khue.joliecafejp.R
import com.khue.joliecafejp.ui.theme.MEDIUM_PADDING
import com.khue.joliecafejp.ui.theme.greyOpacity60Primary
import com.khue.joliecafejp.ui.theme.greyPrimary
import com.khue.joliecafejp.ui.theme.textColor2
import com.khue.joliecafejp.utils.CameraUIAction
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraView(
    onImageCaptured: (Uri) -> Unit,
    onTurnOffCamera: () -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    val context = LocalContext.current
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }

    CameraPreviewView(
        imageCapture,
        lensFacing
    ) { cameraUIAction ->
        when (cameraUIAction) {
            is CameraUIAction.OnCameraClick -> {
                imageCapture.takePicture(context, lensFacing, onImageCaptured, onError)
            }
            is CameraUIAction.OnSwitchCameraClick -> {
                lensFacing =
                    if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT
                    else
                        CameraSelector.LENS_FACING_BACK
            }
            is CameraUIAction.OnTurnOffCameraClick -> {
                onTurnOffCamera()
            }
        }
    }
}

@Composable
private fun CameraPreviewView(
    imageCapture: ImageCapture,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    cameraUIAction: (CameraUIAction) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = androidx.camera.core.Preview.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val previewView = remember { PreviewView(context) }
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {

        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            CameraControls(cameraUIAction)
        }

    }
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

@Composable
fun CameraControls(cameraUIAction: (CameraUIAction) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.greyOpacity60Primary)
            .padding(MEDIUM_PADDING),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CameraControl(
            modifier = Modifier.size(34.dp),
            imageVectorId = R.drawable.ic_switch_camera,
            contentDescId = R.string.switch_camera,
            onClick = { cameraUIAction(CameraUIAction.OnSwitchCameraClick) }
        )

        CameraControl(
            modifier = Modifier.size(34.dp),
            imageVectorId = R.drawable.ic_capture,
            contentDescId = R.string.take_picture,
            onClick = { cameraUIAction(CameraUIAction.OnCameraClick) }
        )

        CameraControl(
            modifier = Modifier.size(34.dp),
            imageVectorId = R.drawable.ic_turnoff_camera,
            contentDescId = R.string.turn_off_camera,
            onClick = {cameraUIAction(CameraUIAction.OnTurnOffCameraClick)}
        )

    }
}


@Composable
fun CameraControl(
    imageVectorId: Int,
    contentDescId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {


    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = imageVectorId),
            contentDescription = stringResource(id = contentDescId),
            modifier = modifier,
            tint = MaterialTheme.colors.textColor2
        )
    }

}

private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"

fun ImageCapture.takePicture(
    context: Context,
    lensFacing: Int,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val name = SimpleDateFormat(FILENAME, Locale.US)
        .format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX")
        }
    }

    val metadata = ImageCapture.Metadata().apply {
        // Mirror image when using the front camera
        isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
    }

    // Create output options object which contains file + metadata
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .setMetadata(metadata)
        .build()

    this.takePicture(
        outputOptions,
        Executors.newSingleThreadExecutor(),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = output.savedUri!!
                onImageCaptured(savedUri)
            }
            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        })
}