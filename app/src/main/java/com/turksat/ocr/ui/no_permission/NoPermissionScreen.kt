package com.turksat.ocr.ui.no_permission

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NoPermissionScreen(onRequestPermission: ()->Unit) {
    NoPermissionContent(
        onRequestPermission = onRequestPermission
    )
}


@SuppressLint("ComposeModifierMissing")
@Composable
fun NoPermissionContent(onRequestPermission: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Lütfen uygulamanın belgenizi taraması için kamera kullanım iznini veriniz."
            )
            Button(onClick = onRequestPermission) {
                Icon(imageVector = Icons.Default.Camera, contentDescription = "Camera")
                Text(text = "Kamera İzni")
            }
        }
    }

}
