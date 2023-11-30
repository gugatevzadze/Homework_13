package com.example.homework_13

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_13.databinding.FragmentInputtedFieldsBinding

class InputtedFieldsFragment : Fragment() {

    private var _binding: FragmentInputtedFieldsBinding? = null
    private val binding get() = _binding!!

    //adapter for parent recycler (the one with the container in name)
    private val adapter = FieldInputContainerAdapter()

    //viewmodel for communication between fragments
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputtedFieldsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewContainer.adapter = adapter
        binding.recyclerViewContainer.layoutManager = LinearLayoutManager(requireContext())

        // Observing changes in the sharedViewModel
        sharedViewModel.fieldInputsContainerList.observe(viewLifecycleOwner) { fieldInputsContainerList ->
            adapter.submitList(fieldInputsContainerList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}