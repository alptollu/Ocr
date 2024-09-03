![OpticCharacter_Recognition](https://github.com/user-attachments/assets/dd0cb043-d6c3-46d6-8082-bf49d7f63f53)


![GitHub last commit (branch)](https://img.shields.io/github/last-commit/alptollu/Ocr/main-coroutine-scope)




### About This Project

This MRZ(Machine Readable Zone)* parser was created during my internship at Turksat, where I gained valuable experience in developing performant and accurate mobile applications using Kotlin and Jetpack Compose. I focused on building a robust solution for extracting data from MRZ fields, particularly for Turkish identity cards, by optimizing the integration of ML Kit's text recognition API and utilizing regular expressions for precise data extraction and validation.

*To understand MRZ you can read : https://regulaforensics.com/blog/machine-readable-zone/

### Installation

This project is an Android application and requires Android Studio to build and run.

**Important:** This project is actively maintained on the `main-coroutine-scope` branch. Please use this branch for the latest features and bug fixes.

To clone the project with the `main-coroutine-scope` branch:

```bash
git clone -b main-coroutine-scope https://github.com/alptollu/Ocr.git
```

### Technologies Used

This project utilizes the following technologies:

* **Kotlin:** The primary programming language used for developing the application.
* **Jetpack Compose:** A modern and declarative UI toolkit for building native Android user interfaces.
* **ML Kit Text Recognition API:** A powerful machine learning API from Google for performing text recognition tasks.
* **Coroutines:** A Kotlin feature for managing asynchronous operations and improving application responsiveness.

### MRZ Data Extraction for Turkish Identity Cards

This project uses ML Kit Text Recognition API to extract the following information from the Machine Readable Zone (MRZ) of Turkish identity cards:

* **Document No (Seri No):** The document number printed on the identity card.
* **TR Identity No (T.C. Kimlik Numarası):** The Turkish Republic identity number.
* **Date of Birth (Doğum Tarihi):** The date of birth of the identity card holder.
* **Valid Until (Geçerlilik Tarihi):** The expiration date of the identity card.

This project is specifically designed to accurately read and process the MRZ data found on Turkish identity cards, ensuring efficient and reliable extraction of the above information.









