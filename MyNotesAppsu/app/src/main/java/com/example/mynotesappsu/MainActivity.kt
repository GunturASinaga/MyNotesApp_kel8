package com.example.mynotesappsu

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesappsu.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findRestaurant()

        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this@MainActivity, EditNoteActivity::class.java))
        }

    }

    private fun findRestaurant() {
        val client = ApiConfig.getApiService().getNotes()
        client?.enqueue(object : Callback<Map<String?, Note?>?> {
            override fun onResponse(
                call: Call<Map<String?, Note?>?>,
                response: Response<Map<String?, Note?>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.isNotEmpty()) {
                        val notes = responseBody.entries.toList()
                        val adapter = NoteAdapter(notes)
                        binding.recyclerView.adapter = adapter
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Map<String?, Note?>?>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    private fun createNote() {
        val note = Note(31052023, "Ini dari tombol", "SPT")
        val client = ApiConfig.getApiService().createNote(note)
        client.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val createdNote = response.body()
                    if (createdNote != null) {
                        // Handle the created note object
                        Log.d(TAG, "Note created - Title: ${createdNote.title}, Desc: ${createdNote.desc}, Date: ${createdNote.date}")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun updateNote() {
        val note = Note(
            date = 31052023,
            desc = "Ini dari tombol (updated)",
            title = "SPT (updated)"
        )

        val key = "-NX6643ES-T808N5gSxU"
        val client = ApiConfig.getApiService().updateNote(key, note)
        client.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val updatedNote = response.body()
                    if (updatedNote != null) {
                        // Handle the updated note object
                        Log.d(TAG, "Note updated - Title: ${updatedNote.title}, Desc: ${updatedNote.desc}, Date: ${updatedNote.date}")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun deleteNote() {
        val key = "-NX6643ES-T808N5gSxU"
        val client = ApiConfig.getApiService().deleteNote(key)
        client.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Handle the successful deletion
                    Log.d(TAG, "Note deleted successfully")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


}