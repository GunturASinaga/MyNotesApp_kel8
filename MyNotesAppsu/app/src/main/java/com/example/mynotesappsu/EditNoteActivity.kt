package com.example.mynotesappsu

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesappsu.databinding.ActivityEditNoteBinding
import com.example.mynotesappsu.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val key = intent.getStringExtra("key")
        val note = intent.getParcelableExtra<Note>("note")

        if (note != null) {
            binding.edtTitle.setText(note.title)
            binding.edtDescription.setText(note.desc)
            binding.btnDelete.visibility = View.VISIBLE
        }

        binding.btnSubmit.setOnClickListener{
            if(note != null){
                updateNote(key.toString(), binding.edtDescription.text.toString(), binding.edtTitle.text.toString())
            } else{
                createNote(binding.edtDescription.text.toString(), binding.edtTitle.text.toString() )
            }
            startActivity(Intent(this@EditNoteActivity, MainActivity::class.java))
        }

        binding.btnDelete.setOnClickListener{
            deleteNote(key.toString())
            startActivity(Intent(this@EditNoteActivity, MainActivity::class.java))
        }
    }

    private fun updateNote(id : String, desc : String, tittle : String) {
        val note = Note(
            date = 29032023,
            desc = desc,
            title = tittle
        )

        val key = id
        val client = ApiConfig.getApiService().updateNote(key, note)
        client.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val updatedNote = response.body()
                    if (updatedNote != null) {
                        // Handle the updated note object
                        Log.d(ContentValues.TAG, "Note updated - Title: ${updatedNote.title}, Desc: ${updatedNote.desc}, Date: ${updatedNote.date}")
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun createNote(desc : String, tittle : String) {
        val currentDate = SimpleDateFormat("ddMMyyyy").format(Date())
        val note = Note(currentDate.toLong(), desc, tittle)
        val client = ApiConfig.getApiService().createNote(note)
        client.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val createdNote = response.body()
                    if (createdNote != null) {
                        // Handle the created note object
                        Log.d(ContentValues.TAG, "Note created - Title: ${createdNote.title}, Desc: ${createdNote.desc}, Date: ${createdNote.date}")
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun deleteNote(id : String) {
        val key = id
        val client = ApiConfig.getApiService().deleteNote(key)
        client.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Handle the successful deletion
                    Log.d(ContentValues.TAG, "Note deleted successfully")
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}