package com.example.translationapp

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.translationapp.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.awaitAll


class MainActivity : AppCompatActivity() {
    /**
     * TranslationApp MainActivity
     * Allows for user input to choose translation methods.
     *
     * Contains radio buttons that choose store the live data regarding
     * source and what to translate to. Contains observers that update the view
     * when live data in the viewModel is changed.
     *
     * @author Timothy Chan
     * @author Kenna Edwards
     */

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: TranslationMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(TranslationMainViewModel::class.java)
        var sourceLang: String = "en"
        var translateLang: String = "es"

        /*
        observers for the Live data in TranslationMainViewModel
        when the liveData changes due to [function 1], the text changes at the same time
         */

        viewModel.textToTranslate.observe(this, Observer {
            Log.d("viewModel.textToTranslate",it.toString())
            Log.d("MainActivity", sourceLang + translateLang)

            if (sourceLang == "detect") {
                detectLanguage(translateLang,it)

            } else {
                val option = TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.fromLanguageTag(sourceLang).toString())
                    .setTargetLanguage(TranslateLanguage.fromLanguageTag(translateLang).toString()).build()
                translate(option, it)
            }

        })

        /*
        observer for the Live data in for finalText
        finalText is just the translated text that is updated by the translate function.
         */
        viewModel.finalText.observe(this, Observer {
            binding.translationText.text = viewModel.finalText.value.toString()
        })

        /*
        observer for the Live data in for finalText
        finalText is just the translated text that is updated by the translate function.
         */

        viewModel.detectedLanguage.observe(this, Observer {
            val option = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.fromLanguageTag(it).toString())
                .setTargetLanguage(TranslateLanguage.fromLanguageTag(translateLang).toString()).build()
            translate(option, viewModel.textToTranslate.value.toString())
        })

        /*
        Takes into account what source option and does it.
        This is constantly looking at the viewModel live data: selectedRadioButtonSource
         */
        viewModel.selectedRadioButtonSource.observe(this, Observer {
            when (it){
                "englishSource" -> sourceLang = "en"
                "spanishSource" -> sourceLang = "es"
                "germanSource" -> sourceLang = "de"
                "detectSource" -> sourceLang = "detect"
                else -> println("Invalid Language Source")
            }
        })

        /*
        Takes into account what translation option and does it.
        This is constantly looking at the viewModel live data: selectedRadioButtonTranslate
         */
        viewModel.selectedRadioButtonTranslate.observe(this, Observer {
            when (it){
                "englishTranslation" -> translateLang = "en"
                "spanishTranslation" -> translateLang = "es"
                "germanTranslation" -> translateLang = "de"
                else -> println("Invalid Language Target")
            }
        })

        val radioGroupSource = binding.radioGroupSource
        val radioGroupTranslate = binding.radioGroupTranslate

        /*
        These two listeners updates the radio group live data in the TranslationMainViewModel
        The values it stores is the @id/ in the XML
         */
        radioGroupSource.setOnCheckedChangeListener { _, checkedId ->
            val resourceName = resources.getResourceEntryName(checkedId)
            viewModel.selectedRadioButtonSource.value = resourceName
            Log.i("SelectedRadioButtonID source", viewModel.selectedRadioButtonSource.value.toString())
        }

        radioGroupTranslate.setOnCheckedChangeListener { _, checkedId ->
            val resourceName = resources.getResourceEntryName(checkedId)
            viewModel.selectedRadioButtonTranslate.value = resourceName
            Log.i("SelectedRadioButtonID translate", viewModel.selectedRadioButtonTranslate.value.toString())
        }

        setContentView(view)
    }

    fun detectLanguage(translateLang:String, translateThis:String){

        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(translateThis)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    Log.i("Detecting Language", "Can't identify language." + translateThis)
                } else {
                    viewModel.detectedLanguage.value = languageCode

                    Log.i("Detecting Language", "Language: $languageCode" + translateThis)
                }
            }
            .addOnFailureListener {
                // Model couldn’t be loaded or other internal error.
            }
    }

    fun translate(options:TranslatorOptions, textToTranslate:String) {
        val translator = Translation.getClient(options)
        getLifecycle().addObserver(translator)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.

                translator.translate(textToTranslate)
                    .addOnSuccessListener { translatedText ->
                        viewModel.finalText.value = translatedText

                        Log.i("MainActivity is translating", translatedText)
                        // Translation successful.
                    }
                    .addOnFailureListener { exception ->
                        Log.e("MainActivity", exception.toString())
                        viewModel.finalText.value = ""
                        // Error.
                    }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", exception.toString())
                viewModel.finalText.value = ""
                // Model couldn’t be downloaded or other internal error.
            }
    }
}