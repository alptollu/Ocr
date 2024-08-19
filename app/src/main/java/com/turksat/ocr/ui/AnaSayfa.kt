package com.turksat.ocr.ui


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.turksat.ocr.ui.camera.CameraScreen
import com.turksat.ocr.ui.no_permission.NoPermissionScreen

@SuppressLint("ComposeParameterOrder")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AnaSayfa(modifier: Modifier = Modifier,navController: NavController) {

    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    CameraContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        navController
    )




}

@Composable
private fun CameraContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    navController: NavController
) {

    if (hasPermission) {
        CameraScreen(navController)
    } else {
        NoPermissionScreen(onRequestPermission)
    }
}