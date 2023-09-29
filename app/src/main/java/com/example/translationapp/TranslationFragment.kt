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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TranslationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TranslationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTranslationBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: TranslationMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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