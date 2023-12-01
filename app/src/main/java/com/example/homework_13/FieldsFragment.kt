package com.example.homework_13

import android.app.DatePickerDialog
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.homework_13.databinding.FragmentFieldsBinding
import java.util.Calendar

//used basefragment
class FieldsFragment : BaseFragment<FragmentFieldsBinding>(FragmentFieldsBinding::inflate) {

    //viewmodel for communication between fragments
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun setUp() {
        //parsing json to initialize fields
        val parserJson = ParserJson()
        val inputStream = resources.openRawResource(R.raw.data)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val fields = parserJson.parseJson(jsonString)

        //displaying fields dynamically based on json
        displayFieldsDynamically(fields)

        //click listeners for registration and navigation
        binding.registerBtn.setOnClickListener { handleRegisterButtonClick(fields) }
        binding.checkBtn.setOnClickListener { navigateToInputtedFieldsFragment() }
    }

    //displaying fields dynamically, flattening them for easier iteration
    private fun displayFieldsDynamically(fields: List<List<FieldsFromJson>>) {
        fields.flatten().filter { it.is_active }.forEach { field ->
            when (field.field_type) {
                "input" -> createInputField(field)
                "chooser" -> createChooserField(field)
            }
        }
    }
    //ui for "input" type
    private fun createInputField(field: FieldsFromJson) {
        val editText = EditText(context)
        setUpFieldAttributes(editText, field)
        binding.linearLayout.addView(editText)
    }
    //ui for "chooser" type
    private fun createChooserField(field: FieldsFromJson) {
        when (field.hint) {
            "Birthday" -> createDatePickerField(field)
            "Gender" -> createGenderRadioGroup(field)
        }
    }
    //setting up common field attributes
    private fun setUpFieldAttributes(editText: EditText, field: FieldsFromJson) {
        editText.hint = field.hint
        editText.inputType = getInputType(field.keyboard)
        editText.tag = field.field_id
    }
    //adding onclick listener to edittext to add date selected in showDatePickerDialog
    private fun createDatePickerField(field: FieldsFromJson) {
        val editText = EditText(context)
        setUpFieldAttributes(editText, field)
        editText.inputType = InputType.TYPE_NULL
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
        editText.setOnClickListener { showDatePickerDialog(editText) }
        binding.linearLayout.addView(editText)
    }

    private fun createGenderRadioGroup(field: FieldsFromJson) {
        //radiogroup so that only one option is selected at a time
        val genderRadioGroup = RadioGroup(context)
        genderRadioGroup.tag = field.field_id
        genderRadioGroup.orientation = RadioGroup.HORIZONTAL

        //adding radiobuttons for different genders
        listOf("Male", "Female", "Other").forEach { gender ->
            val radioButton = RadioButton(context)
            radioButton.id = View.generateViewId()
            radioButton.text = gender
            genderRadioGroup.addView(radioButton)
        }

        binding.linearLayout.addView(genderRadioGroup)
    }

    //passing input fields into validation and if it passes adding it to the list in viewmodel
    private fun handleRegisterButtonClick(fields: List<List<FieldsFromJson>>) {
        val data = mutableListOf<FieldInputs>()
        val allFieldsValid = validateRequiredInputFields(fields, data)

        if (allFieldsValid) {
            sharedViewModel.fieldInputsContainerList.value =
                sharedViewModel.fieldInputsContainerList.value.orEmpty() + FieldInputsContainer(data)
        }
    }
    //validation for fields which have required set to true
    private fun validateRequiredInputFields(
        fields: List<List<FieldsFromJson>>,
        data: MutableList<FieldInputs>
    ): Boolean {
        // Validate each input field and handle required fields
        return fields.all { fieldList ->
            fieldList.all { field ->
                when (val fieldView = binding.root.findViewWithTag<View>(field.field_id)) {
                    is EditText -> validateEditText(field, fieldView, data)
                    is RadioGroup -> true //skipping validation for radiogroup
                    else -> false
                }
            }
        }
    }
    //validating edittext fields that have required set to true
    private fun validateEditText(
        field: FieldsFromJson,
        fieldView: EditText,
        data: MutableList<FieldInputs>
    ): Boolean {
        val isFieldEmpty = fieldView.text.isEmpty()
        return if (field.required && isFieldEmpty) {
            showToast("${field.hint} is required")
            false
        } else if (!field.required && isFieldEmpty) {
            true
        } else {
            data.add(FieldInputs(field.field_id.toString(), fieldView.text.toString()))
            true
        }
    }
    //toast for displaying messages
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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

    //assigning the input type
    private fun getInputType(keyboard: String?): Int {
        // Determine the input type based on the keyboard type
        return when (keyboard) {
            "number" -> InputType.TYPE_CLASS_NUMBER
            else -> InputType.TYPE_CLASS_TEXT
        }
    }

    //navigating to the next fragment
    private fun navigateToInputtedFieldsFragment() {
        findNavController().navigate(R.id.action_fieldsFragment_to_inputtedFieldsFragment)
    }
}
