package com.example.translationapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TranslationMainViewModel : ViewModel() {
    /**
     * Translation View Model.
     *
     * Defines all mutable data variables.
     *
     * @author Timothy Chan
     * @author Kenna Edwards
     */

    /**
    The detected language of the user's text input from detectLanguage() in MainActivity is sent here and then used by MainActivity's translateText text view.
     */
    val detectedLanguage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
    The data from the editText in TranslationFragment is sent here and then used by MainActivity
    */
    val textToTranslate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
    The translated text from translate in MainActivity is sent here and then used by MainActivity's translateText text view.
     */
    val finalText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
    Sample ID stored would be englishSource. Represents the source language chosen by the user. Comes from @id/ field in XML.
     */
    val selectedRadioButtonSource: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
    Sample ID stored would be germanTranslate. Represents the translate to -> language chosen by the user. Comes from @id/ field in XML.
     */
    val selectedRadioButtonTranslate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}