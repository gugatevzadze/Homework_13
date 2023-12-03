package com.example.homework_13

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//view model for communication between fragments
class SharedViewModel: ViewModel() {

    //changed from using livedata to mutablestateflow
    private val _fieldInputsContainerList = MutableStateFlow<List<FieldInputsContainer>>(emptyList())
    val fieldInputsContainerListFlow = _fieldInputsContainerList.asStateFlow()

    fun updateFieldInputsContainerList(newList: List<FieldInputsContainer>) {
        _fieldInputsContainerList.value = newList
    }
}


