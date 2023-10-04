# Project 5 Translation App

Description of the project ...
* Translation application using the ML Kit API.

## Functionality 

The following **required** functionality is completed:

* [ ] User sees a screen with options to translate
* [ ]     FROM: English, Spanish, German, or Detect Language (BONUS)
* [ ]     TO: English, Spanish, or German
* [ ] User chooses the appropriate radio buttons.
* [ ] User can type any sentence and it will be appropriately translated.
* [ ] User can change the language translating to while typing the sentence.
* [ ] User can also change the source language while typing in the phrase.
* [ ] Program uses view binding and view models.

The following **extensions** are implemented:
* androidx.appcompat.app.AppCompatActivity
* androidx.fragment.app.Fragment
* androidx.lifecycle.Observer
* androidx.lifecycle.ViewModelProvider
* androidx.lifecycle.LiveData
* androidx.lifecycle.MutableLiveData
* androidx.lifecycle.ViewModel
* com.google.mlkit.common.model.DownloadConditions
* com.google.mlkit.nl.languageid.LanguageIdentification
* com.google.mlkit.nl.translate.TranslateLanguage
* com.google.mlkit.nl.translate.Translation
* com.google.mlkit.nl.translate.TranslatorOptions

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/chanothy/TranslationApp/blob/master/Project5Demo.gif' title='Video Walkthrough' width='50%' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.
* The biggest challenge was ensuring the viewModel text view was updating as the translating was happening.
* Reason being, the ml kit works asynchronously, so the view model needed to update <ins after /> the translating has completed.  We utilized MutableLiveData<String> updating to combat this problem.

Logic
* The user chooses their source and target language.
* There is an textToTranslate observer:
* IF: the "DetectLanguage" radio button was selected, the detectLanguage() method is called.  This will set the detectedLanguage viewModel variable to the detected language.
* Then, it will translate the user's text input
* ELSE: the user's text input will be immediately translated with the selected source and target languages.
*
* SEE Kdoc comments for more detail.

## License

    Copyright [2023] [Kenna Edwards]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
