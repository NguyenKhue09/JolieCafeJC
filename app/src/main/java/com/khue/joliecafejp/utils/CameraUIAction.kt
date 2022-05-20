package com.khue.joliecafejp.utils

sealed class CameraUIAction {
    object OnCameraClick : CameraUIAction()
    object OnSwitchCameraClick : CameraUIAction()
    object OnTurnOffCameraClick : CameraUIAction()
}
