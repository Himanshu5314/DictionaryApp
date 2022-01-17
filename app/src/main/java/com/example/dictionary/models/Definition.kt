package com.example.dictionary.models

data class Definition(
    val definition: String,
    val example: String,
    val synonyms: List<String>,
    val antonyms: List<String>
)