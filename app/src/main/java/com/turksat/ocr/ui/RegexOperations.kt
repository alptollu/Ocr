package com.turksat.ocr.ui

object RegexOperations {

    fun extractDataFromText(recognizedText: String): List<String> {
        val pattern = Regex("<+([^<]+?)(?=[<]+|$)")
        val dataList= pattern.findAll(recognizedText)
            .map { it.groupValues[1].trim() }
            .toList()
        printDataList(dataList)
        return dataList
    }

    private fun printDataList(dataList: List<String>) {
        println("Listedeki Tüm Objeler:")
        for ((index, item) in dataList.withIndex()) {
            println("Listedeki [${index}] eleman: $item")
        }
    }

    fun extractSeriNo(dataList: List<String>): String {
        val seriNoItem = dataList.find { it.startsWith("TUR") }
        return if (seriNoItem != null) {
            seriNoItem.substringAfter("TUR")
                .replace(" ", "")
                .take(9)
        } else {
            println("Seri no bulunamadı")
            ""
        }
    }

    fun extractTcKimlikNo(dataList: List<String>): String {
        val tcKimlikNoItem = dataList.find {
            val cleanedText = it.replace(" ", "")
            cleanedText.length == 11 && cleanedText.all { char -> char.isDigit() }
        }
        return try {
            if (tcKimlikNoItem != null) {
                tcKimlikNoItem.replace(" ", "")
            } else {
                println("TC Kimlik No bulunamadı")
                ""
            }
        } catch (e: Exception) {
            println("TC Kimlik No çıkarılırken hata oluştu: ${e.message}")
            ""
        }
    }

    fun extractDogumTarihi(dataList: List<String>): String {
        val dogumTarihiItem = dataList.find { it.endsWith("TUR") }
        return try {
            if (dogumTarihiItem != null) {
                val cleanText = dogumTarihiItem.replace(" ", "")
                "${cleanText.substring(4, 6)}.${cleanText.substring(2, 4)}.19${cleanText.substring(0, 2)}"
            } else {
                println("Doğum tarihi bulunamadı")
                ""
            }
        } catch (e: Exception) {
            println("Doğum tarihi çıkarılırken hata oluştu: ${e.message}")
            ""
        }
    }

    fun extractSonGecerlilikTarihi(dataList: List<String>): String {
        val gecerlilikTarihiItem = dataList.find { it.endsWith("TUR") }
        return try {
            if (gecerlilikTarihiItem != null) {
                val cleanText = gecerlilikTarihiItem.replace(" ", "")
                "${cleanText.substring(12, 14)}.${cleanText.substring(10, 12)}.20${cleanText.substring(8, 10)}"
            } else {
                println("Son geçerlilik tarihi bulunamadı")
                ""
            }
        } catch (e: Exception) {
            println("Son geçerlilik tarihi çıkarılırken hata oluştu: ${e.message}")
            ""
        }
    }



    /*fun extractSoyad(dataList: List<String>): String {
        return try {
            if (dataList.size >= 4) {
                dataList[3]
                    .replace(" ", "")
                    .substring(1)
                    .trim()
            } else {
                ""
            }

        }catch ( e: IndexOutOfBoundsException){
            print("Soyadı çıkarılamadı ")
            ""
        }
    }

    fun extractIsim(dataList: List<String>): String {
        return try {if (dataList.size >= 5) {
            val isim1 = dataList[4].replace(" ", "")
            val isim2 = if (dataList.size == 7) dataList[5].replace(" ", "") else ""
            "$isim1 $isim2"
        } else {
            ""
        }
        }catch (e: IndexOutOfBoundsException){
            println("İsim çıkarılamadı")
            ""
        }


    }*/
}