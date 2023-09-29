package com.example.translationapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TranslationMainViewModel : ViewModel() {


    val textToTranslate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    var translatedText = ""

    var selectedRadioButtonSource = -1
    var selectedRadioButtonTranslate = -1
}