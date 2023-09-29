package com.example.translationapp

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
        /**
         * Contains a listener that watches the editText and updates the live data reactively.
         * Every input causes an update.
         */
        // binding
        _binding = FragmentTranslationBinding.inflate(inflater,container,false)
        val view = binding.root

        // Keyword of requireActivity is important to connecting the activity and fragment liveData
        viewModel = ViewModelProvider(requireActivity()).get(TranslationMainViewModel::class.java)
        var editText = binding.editText

        /*
        [function 1]
        This thing basically watches the editText in the fragment and updates the live data
        reactively. You can see what it does if you run the program
        and type something in. Helpful for the instant translation as you input, maybe.
         */
        editText.addTextChangedListener(object : TextWatcher {
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
        super.onDestroyView()
        _binding = null
    }

    /*
    I had this update screen in both here and in ActivityMain,
    but for some reason it's not necessary?? I just leave it here
    incase we need to use it again
     */
//    fun updateScreen() {
//        binding.editText.setText(viewModel.textToTranslate.value)
//    }
}