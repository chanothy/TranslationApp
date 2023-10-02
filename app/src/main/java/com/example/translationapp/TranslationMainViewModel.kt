package com.example.translationapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TranslationMainViewModel : ViewModel() {

    /*
    The data from the editText in TranslationFragment is sent here and then used by MainActivity
     */
    val textToTranslate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /*
    Sample ID stored would be englishSource. Comes from @id/ field in XML.
     */
    val selectedRadioButtonSource: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /*
    Sample ID stored would be germanTranslate. Comes from @id/ field in XML.
     */
    val selectedRadioButtonTranslate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}