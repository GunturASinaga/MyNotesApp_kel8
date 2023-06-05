package com.example.mynotesappsu

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface NotesApi {
    @GET("/")
    fun getNotes(): Call<JsonObject>
}