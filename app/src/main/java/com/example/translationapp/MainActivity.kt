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
        var sourceLang: String = "English"
        var translateLang: String = "Spanish"

        val option1 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.SPANISH).build()
        val option2 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.GERMAN).build()
        val option3 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.SPANISH)
            .setTargetLanguage(TranslateLanguage.GERMAN).build()
        val option4 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.SPANISH)
            .setTargetLanguage(TranslateLanguage.ENGLISH).build()
        val option5 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.GERMAN)
            .setTargetLanguage(TranslateLanguage.SPANISH).build()
        val option6 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.GERMAN)
            .setTargetLanguage(TranslateLanguage.ENGLISH).build()


        /*
        observers for the Live data in TranslationMainViewModel
        when the liveData changes due to [function 1], the text changes at the same time
         */
        var translated = ""
        viewModel.textToTranslate.observe(this, Observer {
            Log.d("viewModel.textToTranslate",it.toString())

            when {
                sourceLang == "English" && translateLang == "Spanish" -> translate(option1,it)
                sourceLang == "English" && translateLang == "German" -> translate(option2,it)
                sourceLang == "Spanish" && translateLang == "German" -> translate(option3,it)
                sourceLang == "Spanish" && translateLang == "English" -> translate(option4,it)
                sourceLang == "German" && translateLang == "Spanish" -> translate(option5,it)
                sourceLang == "German" && translateLang == "English" -> translate(option6,it)
                else -> println("Invalid") //Maybe do a toast asking the user to pick a language?
            }

            println(translated + "Here is the returned value")
//            binding.translationText.text = it.toString()

            binding.translationText.text = translated
        })

        /*
        observer for the Live data in for finalText
        finalText is just the translated text that is updated by the translate function.
         */
        // todo
        viewModel.finalText.observe(this, Observer {
            binding.translationText.text = viewModel.finalText.value.toString()
        })

        /*
        Takes into account what source option and does it.
        This is constantly looking at the viewModel live data: selectedRadioButtonSource
         */
        viewModel.selectedRadioButtonSource.observe(this, Observer {
            when (it){
                "englishSource" -> sourceLang = "English"
                "spanishSource" -> sourceLang = "Spanish"
                "germanSource" -> sourceLang = "German"
                else -> println("Invalid Language Source")
            }

        })

        /*
        Takes into account what translation option and does it.
        This is constantly looking at the viewModel live data: selectedRadioButtonTranslate
         */
        viewModel.selectedRadioButtonTranslate.observe(this, Observer {
            println(it + "The target")
            when (it){
                "englishTranslation" -> translateLang = "English"
                "spanishTranslation" -> translateLang = "Spanish"
                "germanTranslation" -> translateLang = "German"
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

//    fun translate(options:TranslatorOptions, textToTranslate:String):String {
//        val translator = Translation.getClient(options)
//        var translatedTextRet:String = ""
//
//        var conditions = DownloadConditions.Builder()
//            .requireWifi()
//            .build()
//        translator.downloadModelIfNeeded(conditions)
//            .addOnSuccessListener {
//            }
//            .addOnFailureListener { exception ->
//                Log.e("MainActivity", exception.toString())
//                // Model couldn’t be downloaded or other internal error.
//            }
//
//        translator.translate(textToTranslate)
//            .addOnSuccessListener { translatedText ->
//                translatedTextRet= translatedText
//                Log.i("MainActivity is translating", translatedText)
//            }
//            .addOnFailureListener { exception ->
//                Log.e("MainActivity", exception.toString())
//                // Error.
//            }
//        println (translatedTextRet + "This is the translated text within the method")
//
//        lifecycle.addObserver(translator)
//        return "Hello"
//    }

    fun translate(options:TranslatorOptions, textToTranslate:String) {
        val translator = Translation.getClient(options)
        getLifecycle().addObserver(translator)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                // (Set a flag, unhide the translation UI, etc.)

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
                        // ...
                    }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", exception.toString())
                viewModel.finalText.value = ""
                // Model couldn’t be downloaded or other internal error.
                // ...
            }
    }
}