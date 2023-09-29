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



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTranslationBinding.inflate(inflater,container,false)
        val view = binding.root

        // Keyword of requireActivity is important to connecting the activity and fragment liveData
        viewModel = ViewModelProvider(requireActivity()).get(TranslationMainViewModel::class.java)
        updateScreen()

        var editText = binding.editText
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.textToTranslate.value = binding.editText.text.toString()
                Log.d("YourTag", "EditText Value: ${viewModel.textToTranslate.value}")
            }

            override fun afterTextChanged(s: Editable?) {


            }
        })

        return view
    }

    fun updateScreen() {
        binding.editText.setText(viewModel.textToTranslate.value)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TranslationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TranslationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}