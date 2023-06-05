package com.example.mynotesappsu

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class NoteAdapter(private val notes: List<Map.Entry<String?, Note?>>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteEntry = notes[position]
        val key = noteEntry.key
        val note = noteEntry.value
        holder.bind(key, note)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditNoteActivity::class.java).apply {
                putExtra("key", key)
                putExtra("note", note)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.tv_item_id)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_item_date)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_item_title)
        private val descriptionTextView: TextView =
            itemView.findViewById(R.id.tv_item_description)

        fun bind(key: String?, note: Note?) {
            if (key != null) {
                idTextView.text = key
            }

            if (note != null) {
                val date = SimpleDateFormat("ddMMyyyy").parse(note.date.toString())
                val formattedDate = SimpleDateFormat("dd MMM yyyy").format(date)
                dateTextView.text = getFormattedDateWithSuffix(formattedDate)
                titleTextView.text = note.title
                descriptionTextView.text = note.desc
            }
        }

        private fun getFormattedDateWithSuffix(dateString: String): String {
            val day = dateString.substring(0, 2).toInt()
            val suffix = when (day % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
            return dateString.replaceFirst(" ", suffix + " ")
        }
    }
}
