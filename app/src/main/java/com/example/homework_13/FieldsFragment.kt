package com.example.homework_13

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homework_13.databinding.FragmentFieldsBinding
import java.util.Calendar

class FieldsFragment : Fragment() {
    private var _binding: FragmentFieldsBinding? = null
    private val binding get() = _binding!!

    //viewmodel for communication between fragments
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFieldsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //the parser object
        val parserJson = ParserJson()
        val inputStream = resources.openRawResource(R.raw.data)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val fields = parserJson.parseJson(jsonString)

        //displaying parsed json data dynamically
        fields.forEach { fieldList ->
            fieldList.forEach { field ->
                if (field.is_active) {
                    when (field.field_type) {
                        "input" -> {
                            //creating edittext for "input" type fields
                            val editText = EditText(context)
                            editText.hint = field.hint
                            editText.inputType = getInputType(field.keyboard)
                            editText.tag = field.field_id
                            binding.linearLayout.addView(editText)
                        }

                        "chooser" -> {
                            when (field.hint) {
                                "Birthday" -> {
                                    //edittext for date picker
                                    val editText = EditText(context)
                                    editText.hint = field.hint
                                    editText.inputType = InputType.TYPE_NULL
                                    editText.isFocusable = false
                                    editText.isFocusableInTouchMode = false
                                    editText.tag = field.field_id
                                    binding.linearLayout.addView(editText)

                                    //click listener to show date picker
                                    editText.setOnClickListener {
                                        showDatePickerDialog(editText)
                                    }
                                }

                                "Gender" -> {
                                    //radiogroup for gender
                                    val genderRadioGroup = RadioGroup(context)
                                    genderRadioGroup.tag = field.field_id
                                    genderRadioGroup.orientation = RadioGroup.HORIZONTAL

                                    //male radio button
                                    val maleRadioButton = RadioButton(context)
                                    maleRadioButton.id = View.generateViewId()
                                    maleRadioButton.text = "Male"
                                    genderRadioGroup.addView(maleRadioButton)

                                    //female radio button
                                    val femaleRadioButton = RadioButton(context)
                                    femaleRadioButton.id = View.generateViewId()
                                    femaleRadioButton.text = "Female"
                                    genderRadioGroup.addView(femaleRadioButton)

                                    //other gender radio button
                                    val otherRadioButton = RadioButton(context)
                                    otherRadioButton.id = View.generateViewId()
                                    otherRadioButton.text = "Other"
                                    genderRadioGroup.addView(otherRadioButton)

                                    binding.linearLayout.addView(genderRadioGroup)
                                }
                            }
                        }
                    }
                }
            }
        }

        //register button click
        binding.registerBtn.setOnClickListener {
            val data = mutableListOf<FieldInputs>()

            //validating all input fields
            val allFieldsValid = fields.all { fieldList ->
                fieldList.all { field ->
                    val fieldView = binding.root.findViewWithTag<View>(field.field_id)

                    if (fieldView is EditText) {
                        val isFieldEmpty = fieldView.text.isEmpty()

                        if (field.required && isFieldEmpty) {
                            //showing toast if the required field is empty
                            Toast.makeText(
                                requireContext(),
                                "${field.hint} is required",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@all false
                        } else if (!field.required && isFieldEmpty) {
                            //skipping not required fields
                            true
                        } else {
                            //adding field input to the data list
                            data.add(
                                FieldInputs(
                                    field.field_id.toString(),
                                    fieldView.text.toString()
                                )
                            )
                            true
                        }
                    } else if (fieldView is RadioGroup) {
                        val selectedRadioButtonId = fieldView.checkedRadioButtonId

                        if (selectedRadioButtonId != -1) {
                            //adding selected radio button text to the data list
                            val selectedRadioButton =
                                fieldView.findViewById<RadioButton>(selectedRadioButtonId)
                            data.add(
                                FieldInputs(
                                    field.field_id.toString(),
                                    selectedRadioButton.text.toString()
                                )
                            )
                            true
                        } else if (field.required) {
                            //toast to indicate that the field is required
                            Toast.makeText(
                                requireContext(),
                                "${field.hint} is required",
                                Toast.LENGTH_SHORT
                            ).show()
                            false
                        } else {
                            //skipping not required fields
                            true
                        }
                    } else {
                        false
                    }
                }
            }

            //updating the sharedviewmodel with field inputs if they are all valid
            if (allFieldsValid) {
                val currentList = sharedViewModel.fieldInputsContainerList.value.orEmpty()
                sharedViewModel.fieldInputsContainerList.value =
                    currentList + FieldInputsContainer(data)
            }
        }

        //button to navigate to new fragment
        binding.checkBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fieldsFragment_to_inputtedFieldsFragment)
        }
    }

    //showing date picker dialog
    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate =
                    String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    //assigning input type based on the keyboard type
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