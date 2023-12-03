package com.example.homework_13

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_13.databinding.FragmentInputtedFieldsBinding
import kotlinx.coroutines.launch

//used base fragment
class InputtedFieldsFragment : BaseFragment<FragmentInputtedFieldsBinding>(FragmentInputtedFieldsBinding::inflate) {

    //adapter for parent recycler (the one with the container in name)
    private val adapter = FieldInputContainerAdapter()

    //viewmodel for communication between fragments
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun setUp() {

        binding.recyclerViewContainer.adapter = adapter
        binding.recyclerViewContainer.layoutManager = LinearLayoutManager(requireContext())

        //observing changes in the sharedViewModel, this time using the collect function
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.fieldInputsContainerListFlow.collect { fieldInputsContainerList ->
                    adapter.submitList(fieldInputsContainerList)
                }
            }
        }
    }
}
