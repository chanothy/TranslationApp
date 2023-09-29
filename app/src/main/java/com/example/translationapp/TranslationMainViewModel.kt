package com.example.translationapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TranslationMainViewModel : ViewModel() {

    val textToTranslate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val selectedRadioButtonSource: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val selectedRadioButtonTranslate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

//    var textToTranslate = ""
//    var selectedRadioButtonSource = ""
//    var selectedRadioButtonTranslate = ""
}