package com.example.translationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.translationapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: TranslationMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(TranslationMainViewModel::class.java)
        updateScreen()
        viewModel.textToTranslate.observe(this, Observer {
            Log.d("Test",it.toString())
            binding.translationText.text = it.toString()
        })

        setContentView(view)
    }



    fun updateScreen() {
        binding.translationText.text = viewModel.translatedText

    }
}