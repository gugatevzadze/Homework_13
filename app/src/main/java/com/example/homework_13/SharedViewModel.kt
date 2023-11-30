package com.example.homework_13

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//class SharedViewModel: ViewModel() {
//    //list to store inputted field data
//    var fieldInputsList: List<FieldInputs>? = null
//}
class SharedViewModel: ViewModel() {
    val fieldInputsContainerList: MutableLiveData<List<FieldInputsContainer>> = MutableLiveData(listOf())
}


