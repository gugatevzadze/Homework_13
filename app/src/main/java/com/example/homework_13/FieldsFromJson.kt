package com.example.homework_13

data class FieldsFromJson(
    val field_id: Int,
    val hint: String,
    val field_type: String,
    val keyboard: String?,
    val required: Boolean,
    val is_active: Boolean,
    val icon: String
)
