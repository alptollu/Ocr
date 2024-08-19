package com.turksat.ocr.ui.camera



import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.turksat.ocr.ui.RegexOperations
import kotlinx.coroutines.delay

@Composable
fun CameraScreen(navController: NavController) {
    CameraContent(navController)
}

@Composable
private fun CameraContent(navController: NavController) {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }
    var detectedText: String by remember { mutableStateOf("Henüz bir yazı tanımlanmadı...") }
    var hasScanned by remember { mutableStateOf(false) }
    var showDataOnCamera by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lütfen metni taratınız...",
                        fontSize = 14.sp
                    )
                }
            )
        },
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(paddingValues),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                }
            )


            LaunchedEffect(key1 = cameraController) {
                if (!hasScanned) {
                    delay(1500)
                    startTextRecognition(context, cameraController) { recognizedText ->
                        detectedText = recognizedText
                    }
                    hasScanned = true
                    showDataOnCamera = true
                }
            }

            if (showDataOnCamera) {
                val dataList = RegexOperations.extractDataFromText(detectedText)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f))
                        .padding(16.dp)
                        .align(Alignment.TopCenter),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Seri No: ${RegexOperations.extractSeriNo(dataList)}")
                    Text("TC Kimlik No: ${RegexOperations.extractTcKimlikNo(dataList)}")
                    Text("Doğum Tarihi: ${RegexOperations.extractDogumTarihi(dataList)}")
                    Text("Son Geçerlilik Tarihi: ${RegexOperations.extractSonGecerlilikTarihi(dataList)}")
                    Text("İsim: ${RegexOperations.extractIsim(dataList)}")
                    Text("Soyad: ${RegexOperations.extractSoyad(dataList)}")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        val encodedText = Uri.encode(detectedText)
                        navController.navigate("textArea/${encodedText}")
                    }) {
                        Text("OK")
                    }

                    Button(onClick = {
                        navController.navigate("anasayfa")
                        showDataOnCamera = false
                        hasScanned = false
                    }) {
                        Text("Metni Tara")
                    }
                }
            }
        }
    }
}



private fun startTextRecognition(
    context: Context,
    cameraController: LifecycleCameraController,
    onDetectedTextUpdated: (String) -> Unit
) {
    cameraController.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            @OptIn(ExperimentalGetImage::class)
            @ExperimentalGetImage
            override fun onCaptureSuccess(imageProxy: ImageProxy) {
                val image = imageProxy.image
                if (image != null) {
                    val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
                    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    recognizer.process(inputImage)
                        .addOnSuccessListener { visionText ->
                            onDetectedTextUpdated(visionText.text)
                        }
                        .addOnFailureListener { e ->

                        }
                        .addOnCompleteListener {
                            imageProxy.close()
                        }
                }
            }

            override fun onError(exception: ImageCaptureException) {
                println("Imagecapture hatası...")
            }
        }
    )
}
