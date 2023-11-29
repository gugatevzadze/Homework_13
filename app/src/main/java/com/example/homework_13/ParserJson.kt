package com.example.homework_13

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//used gson library for json parsing
class ParserJson {
    private val gson = Gson()

    fun parseJson(jsonString: String): List<List<FieldsFromJson>> {
        return gson.fromJson(jsonString, object : TypeToken<List<List<FieldsFromJson>>>() {}.type)
    }
}