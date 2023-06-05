package com.example.mynotesappsu

import retrofit2.Response
import retrofit2.http.GET

interface BookApiService {
    @GET(".json")
    suspend fun getBooks(): Response<List<Book>>
}