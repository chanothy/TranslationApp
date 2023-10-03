package com.example.translationapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TranslationMainViewModel : ViewModel() {

    /*
    The translated text from translate in MainActivity is sent here and then used by MainActivity's translateText text view.
     */
    val detectedLanguage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /*
        The data from the editText in TranslationFragment is sent here and then used by MainActivity
         */
    val textToTranslate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /*
    The translated text from translate in MainActivity is sent here and then used by MainActivity's translateText text view.
     */
    val finalText: MutableLiveData<String> by lazy {
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