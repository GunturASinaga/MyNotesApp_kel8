package com.example.mynotesappsu

import com.google.gson.annotations.SerializedName

data class Notek(

	@field:SerializedName("auth")
	val auth: List<AuthItem?>? = null,

	@field:SerializedName("notes-app")
	val notesApp: List<NotesAppItem?>? = null
)

data class NotesAppItem(

	@field:SerializedName("date")
	val date: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class AuthItem(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
