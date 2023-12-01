package com.example.homework_13

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//view model for communication between fragments
class SharedViewModel: ViewModel() {
    //live data to track changes in the list
    val fieldInputsContainerList: MutableLiveData<List<FieldInputsContainer>> = MutableLiveData(listOf())
}


