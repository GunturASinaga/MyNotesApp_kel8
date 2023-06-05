package com.example.mynotesappsu

import com.google.gson.annotations.SerializedName

data class Response3(

	@field:SerializedName("notes")
	val notes: List<NotesItem?>? = null
)

data class NotesItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)
