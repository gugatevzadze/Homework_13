package com.example.homework_13

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_13.databinding.FieldInputContainerBinding

//adapter for displaying the fields together in one section lets say
class FieldInputContainerAdapter :
    ListAdapter<FieldInputsContainer, FieldInputContainerAdapter.ContainerViewHolder>(
        ContainerDiffCallback()
    ) {

    inner class ContainerViewHolder(val binding: FieldInputContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FieldInputsContainer) {
            val childAdapter = FieldInputItemAdapter()
            binding.recyclerView.layoutManager = LinearLayoutManager(binding.recyclerView.context)
            binding.recyclerView.adapter = childAdapter
            childAdapter.submitList(item.fieldInputsList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FieldInputContainerBinding.inflate(inflater, parent, false)
        return ContainerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContainerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class ContainerDiffCallback : DiffUtil.ItemCallback<FieldInputsContainer>() {
        override fun areItemsTheSame(
            oldItem: FieldInputsContainer,
            newItem: FieldInputsContainer
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FieldInputsContainer,
            newItem: FieldInputsContainer
        ): Boolean {
            return oldItem == newItem
        }
    }
}