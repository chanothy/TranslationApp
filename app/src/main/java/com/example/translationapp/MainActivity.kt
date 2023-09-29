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

        /*
        observers for the Live data in TranslationMainViewModel
        when the liveData changes due to [function 1], the text changes at the same time
         */
        viewModel.textToTranslate.observe(this, Observer {
            Log.d("viewModel.textToTranslate",it.toString())
            binding.translationText.text = it.toString()
        })

        /*
        Takes into account what source option and does it.
        This is constantly looking at the viewModel live data: selectedRadioButtonSource
         */
        viewModel.selectedRadioButtonSource.observe(this, Observer {
            // todo
        })

        /*
        Takes into account what translation option and does it.
        This is constantly looking at the viewModel live data: selectedRadioButtonTranslate
         */
        viewModel.selectedRadioButtonTranslate.observe(this, Observer {
            // todo
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
}