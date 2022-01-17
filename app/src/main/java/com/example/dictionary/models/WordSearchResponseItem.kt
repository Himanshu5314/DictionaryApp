package com.example.dictionary.models

data class WordSearchResponseItem(
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val word: String
)