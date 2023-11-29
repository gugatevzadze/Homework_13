package com.example.homework_13

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.homework_13.databinding.FragmentFieldsBinding

class FieldsFragment : Fragment() {
    private var _binding: FragmentFieldsBinding? = null
    private val binding get() = _binding!!
    private val adapter = FieldInputItemAdapter()
    //viewmodel for communication between fragments
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFieldsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //creating the parser object
        val parserJson = ParserJson()
        //fields from JSON file
        val inputStream = resources.openRawResource(R.raw.data)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val fields = parserJson.parseJson(jsonString)
        //displaying input fields dynamically based on the parsed data
        fields.forEach { fieldList ->
            fieldList.forEach { field ->
                if (field.is_active) {
                    when (field.field_type) {
                        "input" -> {
                            val editText = EditText(context)
                            editText.hint = field.hint
                            editText.inputType = getInputType(field.keyboard)
                            editText.tag = field.field_id
                            binding.linearLayout.addView(editText)
                        }
                    }
                }
            }
        }
        //list to store user data
        val data = mutableListOf<FieldInputs>()

        //register button functionality
        binding.registerBtn.setOnClickListener {
            val allFieldsValid = fields.all { fieldList ->
                fieldList.all { field ->
                    val editText = binding.root.findViewWithTag<EditText>(field.field_id)
                    if (field.required && editText.text.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "${field.hint} is not filled",
                            Toast.LENGTH_SHORT
                        ).show()
                        false
                    } else {
                        //storing the data in the list
                        if (field.field_type == "input") {
                            data.add(
                                FieldInputs(
                                    field.field_id.toString(),
                                    editText.text.toString()
                                )
                            )
                        }
                        true
                    }
                }
            }

            if (allFieldsValid) {
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                sharedViewModel.fieldInputsList = data
            }
        }
        //check button to see the list and navigate to another fragment (i couldnt come up with anything else)
        binding.checkBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fieldsFragment_to_inputtedFieldsFragment)
        }
    }
    //assigning the input type based on the keyboard type
    private fun getInputType(keyboard: String?): Int {
        return when (keyboard) {
            "number" -> InputType.TYPE_CLASS_NUMBER
            else -> InputType.TYPE_CLASS_TEXT
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

