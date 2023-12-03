package com.example.homework_13

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_13.databinding.FieldInputItemBinding

//adapter for displaying individual field inputs in a recyclerview
class FieldInputItemAdapter : ListAdapter<FieldInputs, FieldInputItemAdapter.ItemViewHolder>(ItemDiffCallback()) {
    inner class ItemViewHolder(val binding: FieldInputItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FieldInputItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.apply {
            binding.fieldKey.text = item.fieldId
            binding.fieldValue.text = item.fieldValue
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<FieldInputs>() {
        override fun areItemsTheSame(oldItem: FieldInputs, newItem: FieldInputs): Boolean {
            return oldItem.fieldId == newItem.fieldId
        }

        override fun areContentsTheSame(oldItem: FieldInputs, newItem: FieldInputs): Boolean {
            return oldItem == newItem
        }
    }
}
