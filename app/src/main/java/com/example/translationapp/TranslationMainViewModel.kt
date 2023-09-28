package com.example.translationapp

import androidx.lifecycle.ViewModel

class TranslationMainViewModel : ViewModel() {
    var textToTranslate = ""
    var translatedText = ""
    var selectedRadioButtonSource = -1
    var selectedRadioButtonTranslate = -1
}