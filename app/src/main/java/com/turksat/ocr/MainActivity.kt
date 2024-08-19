package com.turksat.ocr

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.navigation.NavType


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.turksat.ocr.ui.AnaSayfa
import com.turksat.ocr.ui.camera.CameraScreen

import com.turksat.ocr.ui.textarea.TextArea
import com.turksat.ocr.ui.theme.OcrTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OcrTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    PageNavigation()
                }
            }
        }
    }
}




@Composable
fun PageNavigation(){
    val navController = rememberNavController()
    NavHost(navController= navController, startDestination = "textArea"){

        composable("textArea/{detectedText}", arguments = listOf(
            navArgument("detectedText") { type = NavType.StringType }
        )
        ) { backStackEntry ->
            val detectedText = backStackEntry.arguments?.getString("detectedText") ?: ""
            TextArea(navController, detectedText) // Call TextArea with detectedText
        }

        composable("anaSayfa"){
            AnaSayfa(navController=navController)
        }

        composable("textArea"){
            TextArea(navController=navController,"")
        }

        composable("cameraScreen") {
            CameraScreen(navController)}

    }

}


