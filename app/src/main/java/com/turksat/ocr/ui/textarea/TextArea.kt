package com.turksat.ocr.ui.textarea

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.turksat.ocr.ui.RegexOperations

@Composable
@SuppressLint("ComposeModifierMissing")
fun TextArea(navController: NavController, detectedText: String) {
    var recognizedText by remember { mutableStateOf(detectedText) }
    var showDialog by remember { mutableStateOf(false) }


    val dataList = RegexOperations.extractDataFromText(recognizedText)
    var seriNo by remember { mutableStateOf(RegexOperations.extractSeriNo(dataList)) }
    var tcKimlikNo by remember { mutableStateOf(RegexOperations.extractTcKimlikNo(dataList)) }
    var dogumTarihi by remember { mutableStateOf(RegexOperations.extractDogumTarihi(dataList)) }
    var sonGecerlilikTarihi by remember { mutableStateOf(RegexOperations.extractSonGecerlilikTarihi(dataList)) }
    //var soyad by remember { mutableStateOf(RegexOperations.extractSoyad(dataList)) }
    //var isim by remember { mutableStateOf(RegexOperations.extractIsim(dataList)) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(value = seriNo, onValueChange = { seriNo = it }, label = { Text("Seri No") })
        TextField(value = tcKimlikNo, onValueChange = { tcKimlikNo = it }, label = { Text("TC Kimlik No") })
        TextField(value = dogumTarihi, onValueChange = { dogumTarihi = it }, label = { Text("Doğum Tarihi") })
        TextField(value = sonGecerlilikTarihi, onValueChange = { sonGecerlilikTarihi = it }, label = { Text("Son Geçerlilik Tarihi") })
        //TextField(value = isim, onValueChange = { isim = it }, label = { Text("İsim") })
        //TextField(value = soyad, onValueChange = { soyad = it }, label = { Text("Soyadı") })

        Button(onClick = { showDialog = true }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Camera",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text("Tara")
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Hazırlık") },
                text = { Text("Lütfen nüfus cüzdanınızın arka yüzünü kamera karşısında hazır olarak tutunuz.") },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        navController.navigate("anasayfa")
                    }) {
                        Text("Ok")
                    }
                }
            )
        }
    }
}