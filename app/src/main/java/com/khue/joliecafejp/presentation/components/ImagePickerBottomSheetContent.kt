package com.khue.joliecafejp.presentation.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.OpenableColumns
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.khue.joliecafejp.BuildConfig
import com.khue.joliecafejp.R
import com.khue.joliecafejp.firebase.firebase_storage.FirebaseStorageImpl
import com.khue.joliecafejp.ui.theme.*
import java.io.IOException


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun ImagePickerBottomSheetContent(
    onHideImagePickerBottomSheet: () -> Unit,
    updateUserData: (String) -> Unit
) {

    val uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)

    val intent =
        Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
    val requiredPermissions =
        mutableListOf(
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q && Environment.isExternalStorageLegacy()) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

    val context = LocalContext.current

    val firebaseStorageImpl = FirebaseStorageImpl.getInstance()

    var isTakePicture by rememberSaveable {
        mutableStateOf(false)
    }

    val getFile =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Uri? = result.data?.data
                val root = FirebaseAuth.getInstance().currentUser?.uid
                if (data != null) {
                    try {
                        val fileName = getFileName(uri = data, context = context)
                        root?.let {
                            firebaseStorageImpl.uploadFile(
                                file = data,
                                fileName = fileName,
                                root = root,
                                context = context,
                                updateUserData = updateUserData
                            )
                        }
                        Toast.makeText(context, fileName, Toast.LENGTH_SHORT).show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            context,
                            "Get image failed!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    val getFilePermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(
                    context,
                    "Permission granted",
                    Toast.LENGTH_LONG
                ).show()
                getImageLauncher(getFile = getFile)
            } else {
                Toast.makeText(
                    context,
                    "Oops, you just denied the permission for storage, You can also allow it from setings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    val getManageFile =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val getTakeImagePermissionsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGranted ->
            println(isGranted)
            if (!isGranted.containsValue(false)) {
                Toast.makeText(
                    context,
                    "Permissions granted",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Oops, you just denied the permission, You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.greyOpacity60Primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = EXTRA_LARGE_PADDING, vertical = EXTRA_EXTRA_SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.action),
                fontFamily = raleway,
                color = MaterialTheme.colors.textColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    if (isTakePicture) isTakePicture = false
                    onHideImagePickerBottomSheet()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = stringResource(
                        R.string.close
                    ),
                    tint = MaterialTheme.colors.textColor
                )
            }
        }

        Divider(color = MaterialTheme.colors.textColor, thickness = 1.dp)

        AnimatedVisibility(visible = !isTakePicture) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = EXTRA_LARGE_PADDING)
                        .clickable {
                            if (
                                checkPermissionGranted(
                                    context = context,
                                    permission = Manifest.permission.READ_EXTERNAL_STORAGE
                                )
                            ) {
                                getImageLauncher(getFile = getFile)
                            } else {
                                requestGetFilePermission(getFilePermissionLauncher = getFilePermissionLauncher)
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier.padding(
                            vertical = MEDIUM_PADDING,
                            horizontal = EXTRA_LARGE_PADDING
                        ),
                        painter = painterResource(id = R.drawable.ic_gallery),
                        contentDescription = stringResource(R.string.gallery),
                        tint = MaterialTheme.colors.textColor
                    )
                    Text(
                        text = stringResource(R.string.choose_from_gallery),
                        color = MaterialTheme.colors.textColor,
                        fontFamily = ralewayMedium,
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = EXTRA_LARGE_PADDING, vertical = SMALL_PADDING),
                    color = MaterialTheme.colors.textColor,
                    thickness = 0.5.dp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (
                                allPermissionsGranted(context, requiredPermissions)
                            ) {
                                isTakePicture = true
                            } else {
                                requestAllPermission(
                                    getManageFile = getManageFile,
                                    getPermissionsLauncher = getTakeImagePermissionsLauncher,
                                    intent = intent,
                                    requiredPermissions = requiredPermissions
                                )
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier.padding(
                            vertical = MEDIUM_PADDING,
                            horizontal = EXTRA_LARGE_PADDING
                        ),
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = stringResource(R.string.camera),
                        tint = MaterialTheme.colors.textColor
                    )
                    Text(
                        text = stringResource(R.string.camera),
                        color = MaterialTheme.colors.textColor,
                        fontFamily = ralewayMedium,
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
                }

                Button(
                    modifier = Modifier
                        .padding(horizontal = EXTRA_LARGE_PADDING, vertical = EXTRA_LARGE_PADDING)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.textColor
                    ),
                    contentPadding = PaddingValues(all = MEDIUM_SMALL_PADDING),
                    onClick = onHideImagePickerBottomSheet
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        fontFamily = ralewayMedium,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.buttonTextColor
                    )
                }
            }
        }

        AnimatedVisibility(visible = isTakePicture) {
            CameraView(
                onImageCaptured = { uri: Uri ->

                },
                onTurnOffCamera = {
                    isTakePicture = false
                },
                onError = {

                }
            )
        }
    }


}


private fun getImageLauncher(getFile: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    var getFileIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "image/*"
    }
    getFileIntent = Intent.createChooser(getFileIntent, "Select picture")
    getFile.launch(getFileIntent)
}

private fun checkPermissionGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

private fun requestGetFilePermission(getFilePermissionLauncher: ManagedActivityResultLauncher<String, Boolean>) {
    getFilePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
}

private fun allPermissionsGranted(context: Context, requiredPermissions: Array<String>): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager() && requiredPermissions.all {
            ContextCompat.checkSelfPermission(
                context, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    } else {
        requiredPermissions.all {
            ContextCompat.checkSelfPermission(
                context, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}

private fun requestAllPermission(
    getManageFile: ManagedActivityResultLauncher<Intent, ActivityResult>,
    getPermissionsLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards
    Boolean>>,
    intent: Intent,
    requiredPermissions: Array<String>
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        getManageFile.launch(intent)
        getPermissionsLauncher.launch(requiredPermissions)
    } else {
        getPermissionsLauncher.launch(requiredPermissions)
    }
}

@SuppressLint("Range")
private fun getFileName(uri: Uri, context: Context): String {
    var res: String? = null
    if (uri.scheme.equals("content")) {
        val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
        cursor.use { cur ->
            if (cur.moveToFirst()) {
                res = cur.getString(cur.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (res == null) {
        res = uri.path!!.split('/').also {
            it[it.lastIndex]
        }.toString()
    }
    return res as String
}
