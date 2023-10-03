# Project 5 Translation App

Description of the project ...
* Translation application using an API.

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
* A major challenge I faced was the plugins.  I did not correctly implement all of the plugins, which led to several hours of debugging the issue.
* Another challenge was passing arguments between screens.  It was difficult to ensure the nav_graph.xml was correctly implemented.

Functionality Note
* For division, the user's answer is equal to the Integer. AKA if the question is 9/8, the answer is 1.

Logic
* The user chooses their difficulty level, which defines what operands will be displayed in the questions.
* "easy" = 1 <= operand < 10
* "medium" = 1 <= operand < 25
* "hard" = 1 <= operand < 50

* When the user submits an answer, the solve() function evaluates and returns the correct answer to the math problem.  An if statement in the "done" button evaluates whether the correct answer matches the users input.  If yes, the number of correct answers increases by 1.  For more details, see kDocs within QuestionFragment.kt

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
