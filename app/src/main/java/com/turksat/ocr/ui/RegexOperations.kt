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
        for (item in dataList) {
            println(item)
        }
    }

    fun extractSeriNo(dataList: List<String>): String {
        return try {
            if (dataList.isNotEmpty()) {
                dataList[0].substringAfter("TUR")
                    .replace(" ", "")
                    .take(9)
            } else {
                ""
            }
        }catch(e: IndexOutOfBoundsException){
            println("Seri no çıkarılamadı")
            ""
        }
    }

    fun extractTcKimlikNo(dataList: List<String>): String {
        return try {
            if (dataList.size >= 2) {
                dataList[1]
                    .replace(" ", "")
                    .take(11)
            } else {
                ""
            }

        } catch( e: IndexOutOfBoundsException){
            println("Tc kimlik no çıkarılamadı")
            ""
        }
    }

    fun extractDogumTarihi(dataList: List<String>): String {
        return try {
            if (dataList.size >= 3) {
                val cleanText = dataList[2].replace(" ", "")
                "${cleanText.substring(4, 6)}.${cleanText.substring(2, 4)}.19${cleanText.substring(0, 2)}"
            } else {
                ""
            }
        }catch (e : IndexOutOfBoundsException){
            println("Dogum tarihi çıkarılamadı")
            ""
        }
    }

    fun extractSonGecerlilikTarihi(dataList: List<String>): String {
        return try {
            if (dataList.size >= 3) {
                val cleanText = dataList[2].replace(" ", "")
                "${cleanText.substring(12, 14)}.${cleanText.substring(10, 12)}.20${cleanText.substring(8, 10)}"
            } else {
                ""
            }

        }catch(e: IndexOutOfBoundsException){
            println("Son gecerlilik tarihi çıkarılamadı")
            ""
        }
    }



    fun extractSoyad(dataList: List<String>): String {
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


    }
}