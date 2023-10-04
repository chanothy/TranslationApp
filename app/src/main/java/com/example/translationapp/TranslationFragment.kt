package com.example.translationapp

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.TextWatcher;
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.translationapp.databinding.FragmentTranslationBinding

class TranslationFragment : Fragment() {
    /**
     * Translation fragment.
     *
     * Contains a single editText that connects to the viewModel
     * and allows for input of textToTranslate. Updates the live data in the viewModel with input.
     *
     * @property _binding - binding for finding views
     * @property viewModel - viewModel for storing live data
     *
     * @author Timothy Chan
     */

    private var _binding: FragmentTranslationBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: TranslationMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTranslationBinding.inflate(inflater,container,false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(TranslationMainViewModel::class.java)

        /**
         * [editText]: EditText, defined in activity_main.xml. This is where user inputs their text to be translated.
         * Observes the editText in the fragment and updates the live data reactively.
         */
        var editText = binding.editText

        editText.addTextChangedListener(object : TextWatcher {
            /**
             * Adds a listener, which responds to when the user inputs data.
             * Updates [viewModel.textToTranslate]
             */
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            // when the text changes, it updates the textToTranslate live data in the viewModel.
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.textToTranslate.value = binding.editText.text.toString()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        return view
    }

    override fun onDestroyView() {
        /**
         * Destroys all views.
         */
        Log.i("TranslationFragment.kt", "Destroying view")
        super.onDestroyView()
        _binding = null
    }

}