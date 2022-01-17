package com.example.dictionary.repository

import com.example.dictionary.models.WordSearchResponseData
import com.example.dictionary.retrofit.RetrofitService

class DictionaryRepository(private val dictionaryService: RetrofitService) {
    suspend fun getDefinitions(word: String): WordSearchResponseData? {
        val response = dictionaryService.getDefinitions(word)
        return if (response?.body() != null) {
            response.body()
        } else {
            null
        }
    }
}