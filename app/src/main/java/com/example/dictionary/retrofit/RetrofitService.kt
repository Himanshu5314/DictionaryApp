package com.example.dictionary.retrofit

import com.example.dictionary.models.WordSearchResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("/api/v2/entries/en/{word}")
    suspend fun getDefinitions(
        @Path("word") word: String
    ): Response<WordSearchResponseData>
}