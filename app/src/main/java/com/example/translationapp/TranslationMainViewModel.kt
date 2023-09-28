package com.example.translationapp

import androidx.lifecycle.ViewModel

class TranslationMainViewModel : ViewModel() {
    val textToTranslate = "textToTranslateTest"
    val translatedText = "translatedTest"
    val selectedRadioButtonSource = -1
    val selectedRadioButtonTranslate = -1
}