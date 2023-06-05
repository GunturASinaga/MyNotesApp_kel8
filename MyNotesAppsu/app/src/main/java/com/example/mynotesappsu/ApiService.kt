package com.example.mynotesappsu

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("notes-app.json")
    fun getNotes(): Call<Map<String?, Note?>?>?

    @POST("notes-app.json")
    fun createNote(@Body note: Note): Call<Note>

    @PUT("notes-app/{key}.json")
    fun updateNote(@Path("key") key: String, @Body note: Note): Call<Note>

    @DELETE("notes-app/{key}.json")
    fun deleteNote(
        @Path("key") key: String
    ): Call<Void>
}