package com.example.homework_13

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_13.databinding.FragmentInputtedFieldsBinding

class InputtedFieldsFragment : Fragment() {

    private var _binding: FragmentInputtedFieldsBinding? = null
    private val binding get() = _binding!!

    //adapter
    private val adapter = FieldInputItemAdapter()
    //viewmodel for communication between fragments
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputtedFieldsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //retrieving the list of inputted fields from the viewmodel
        val itemList = sharedViewModel.fieldInputsList

        //setting up the recycler view
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitList(itemList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}