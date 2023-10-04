package com.example.translationapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.translationapp.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions


class MainActivity : AppCompatActivity() {
    /**
     * TranslationApp MainActivity
     * Allows for user input to choose translation methods.
     *
     * Contains radio buttons that choose store the live data regarding
     * source and what to translate to. Contains observers that update the view
     * when live data in the viewModel is changed.
     *
     * @property binding - binding for finding views
     * @property viewModel - viewModel for storing live data
     *
     * @author Timothy Chan
     * @author Kenna Edwards
     */

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: TranslationMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(TranslationMainViewModel::class.java)
        val view = binding.root

        /**
         * [sourceLang]: String- represents the source language for the translation.
         *      Default value= "en" AKA English.
         *      Changes in the radioGroupSource.
         * [translateLang]: String- represents the target language for the translation.
         *      Default value= "es" AKA Spanish.
         *      Changes in the radioGroupTranslate.
         */
        var sourceLang: String = "en"
        var translateLang: String = "es"

        /**
         * [radioGroupSource]: RadioGroup- contains source language radio buttons in activity_main.xml
         * [radioGroupTranslate]: RadioGroup- contains translate -> language radio buttons in activity_main.xml
         */
        val radioGroupSource = binding.radioGroupSource
        val radioGroupTranslate = binding.radioGroupTranslate


        viewModel.textToTranslate.observe(this, Observer {
            /**
             *  @parameter [it]: a String- the user's text input that needs to be translated
             *
             * A View Model Observer for the Live data variable [textToTranslate] defined in TranslationMainViewModel.kt.
             *
             * When the liveData changes due to the [editText.addTextChangedListener] in TranslationFragment.kt
             * the text changes at the same time.
             *
             * IF [sourceLang] = "detect", AKA the user wants the program to auto-detect the source language,
             *   detectLanguage() is called, and the value of [viewModel.textToTranslate] is sent as the param.
             * ELSE translate() is called, and a TranslationOptions object [option] with the chosen
             *   [sourceLang] and [translateLang] is sent as the first param,
             *   and the value of [viewModel.textToTranslate] is sent as the second param.
             */

            if (sourceLang == "detect") {
                detectLanguage(it)

            } else {
                val option = TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.fromLanguageTag(sourceLang).toString())
                    .setTargetLanguage(TranslateLanguage.fromLanguageTag(translateLang).toString()).build()
                translate(option, it)
            }

        })

        viewModel.finalText.observe(this, Observer {
            /**
             *  @parameter [it]: a String- the value of the translated text
             *
             * A View Model Observer for the Live data variable [finalText] defined in TranslationMainViewModel.kt.
             *
             * [binding.translationText]: TextView- the text view that displays the translated text to the user.
             *   Defined in activity_main.xml. It is assigned the value of [finalText]
             * [finalText]: String- The ~translated~ text that is updated by the translate().
             */

            binding.translationText.text = viewModel.finalText.value.toString()
        })

        viewModel.detectedLanguage.observe(this, Observer {
            /**
             *  @parameter [it]: a String- the detected language to be used for translation
             *
             * A View Model Observer for the Live data variable [detectedLanguage] defined in TranslationMainViewModel.kt.
             *
             * When the user selects the "Detect Language" radio button from [selectedRadioButtonSource],
             * [textToTranslate] calls [detectLanguage()], which updates this variable.
             *
             * translate() is called, and
             * a TranslationOptions object [option] with the [detectedLanguage.value] AKA [it]
             * and [translateLang] is sent as the first param,
             * and the value of [viewModel.textToTranslate] is sent as the second param.
             *
             */

            val option = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.fromLanguageTag(it).toString())
                .setTargetLanguage(TranslateLanguage.fromLanguageTag(translateLang).toString()).build()
            translate(option, viewModel.textToTranslate.value.toString())
        })

        viewModel.selectedRadioButtonSource.observe(this, Observer {
            /**
             *  @parameter [it]: a String- the @id/ for the selected radio button
             * A View Model Observer for the Live data variable [selectedRadioButtonSource] defined in TranslationMainViewModel.kt.
             * This is updated according to the activity of [radioGroupSource] in activity_main.kt
             *
             * Assigns the appropriate value to [sourceLang]
             */

            when (it) {
                "englishSource" -> sourceLang = "en"
                "spanishSource" -> sourceLang = "es"
                "germanSource" -> sourceLang = "de"
                "detectSource" -> sourceLang = "detect"
                else -> println("Invalid Language Source")
            }
        })

        viewModel.selectedRadioButtonTranslate.observe(this, Observer {
            /**
             * @parameter [it]: a String- the @id/ for the selected radio button
             *
             * A View Model Observer for the Live data variable [selectedRadioButtonTranslate] defined in TranslationMainViewModel.kt.
             * This is updated according to the activity of [radioGroupTranslate] in activity_main.kt
             *
             * Assigns the appropriate value to [translateLang]
             */

            when (it){
                "englishTranslation" -> translateLang = "en"
                "spanishTranslation" -> translateLang = "es"
                "germanTranslation" -> translateLang = "de"
                else -> println("Invalid Language Target")
            }
        })

        radioGroupSource.setOnCheckedChangeListener { _, checkedId ->
            /**
             * Listener updates the radio group's selectedRadioButtonSource: MutableLiveData<String> value in TranslationMainViewModel.kt
             * @parameter [checkedId]: An Int- The values it stores is the @id/ from activity_main.xml
             */

            val resourceName = resources.getResourceEntryName(checkedId)
            viewModel.selectedRadioButtonSource.value = resourceName
            Log.i("SelectedRadioButtonID source", viewModel.selectedRadioButtonSource.value.toString())
        }

        radioGroupTranslate.setOnCheckedChangeListener { _, checkedId ->
            /**
             * Listener updates the radio group's selectedRadioButtonTranslate: MutableLiveData<String> value in TranslationMainViewModel.kt
             * @parameter [checkedId]: An Int- The values it stores is the @id/ from activity_main.xml
             */

            val resourceName = resources.getResourceEntryName(checkedId)
            viewModel.selectedRadioButtonTranslate.value = resourceName
            Log.i("SelectedRadioButtonID translate", viewModel.selectedRadioButtonTranslate.value.toString())
        }
        setContentView(view)
    }

    fun detectLanguage(translateThis:String) {
        /**
         * @parameter [translateThis]: String- The user's text input, which is used to detect the language
         *
         * Function utilizes the MLKit API to detect the (most probable) language associated with the input string.
         *
         * IF [languageCode] = "und", the text does not correlate closely enough with any language.
         * ELSE [viewModel.detectedLanguage] is set to the [languageCode],
         *  which will activate the [viewModel.detectedLanguage] observer.
         *  This will call translate with the designated source language determined in this function.
         */

        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(translateThis)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    Log.e("MainActivity.kt", "Can't identify language from: " + translateThis)
                } else {
                    viewModel.detectedLanguage.value = languageCode

                    Log.i("MainActivity.kt", "Language detected: $languageCode" + " from " + translateThis)
                }
            }
            .addOnFailureListener {
                Log.e("MainActivity.kt", "Detect a language failed.")
            }
    }

    fun translate(options:TranslatorOptions, textToTranslate:String) {
        /**
         * @parameter [options]: TranslatorOptions object- defines the source and target languages for the translation
         * @parameter [textToTranslate]: String- user's input string to be translated
         *
         * Function utilizes the MLKit API to translate the given text, using the [options], from the source to the target language.
         * Once the model needed for the given [options] is downloaded, the translator translates the [textToTranslate].
         *
         * IF the translation is successful,
         *  Then, [viewModel.finalText] is assigned to the [translatedText].
         *  This will trigger the [viewModel.finalText] observer, which will update the [binding.translationText] text view.
         * ELSE [viewModel.finalText] = "" AKA the [binding.translationText] text view will not have translated text.
         */
        val translator = Translation.getClient(options)
        getLifecycle().addObserver(translator)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully.
                translator.translate(textToTranslate)
                    .addOnSuccessListener { translatedText ->
                        viewModel.finalText.value = translatedText

                        Log.i("MainActivity.kt", "translated to: " + translatedText)
                        // Translation successful.
                    }
                    .addOnFailureListener { exception ->
                        Log.e("MainActivity.kt", exception.toString())
                        viewModel.finalText.value = ""
                    }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity.kt", exception.toString())
                viewModel.finalText.value = ""
            }
    }
}