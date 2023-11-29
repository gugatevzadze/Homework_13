package com.example.homework_13

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_13.databinding.FieldInputItemBinding

class FieldInputItemAdapter : ListAdapter<FieldInputs, FieldInputItemAdapter.YourViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FieldInputItemBinding.inflate(inflater, parent, false)
        return YourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YourViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class YourViewHolder(private val binding: FieldInputItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FieldInputs) {
            binding.fieldKey.text = item.fieldId
            binding.fieldValue.text = item.fieldValue
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<FieldInputs>() {
        override fun areItemsTheSame(oldItem: FieldInputs, newItem: FieldInputs): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FieldInputs, newItem: FieldInputs): Boolean {
            return oldItem == newItem
        }
    }
}