package com.example.granne

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonFindMatchRecycleViewAdapter(val context: Context, val persons: List<PersonFindMatch>) :
    RecyclerView.Adapter<PersonFindMatchRecycleViewAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {

        return persons.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item_match, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]

        holder.nameTextView.text = person.name
        holder.interestTextView.text = person.interests
        holder.imageView.setImageResource(R.drawable.userimage)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView = itemView.findViewById<TextView>(R.id.tvName)
        val interestTextView = itemView.findViewById<TextView>(R.id.tvInterests)
        val imageView = itemView.findViewById<ImageView>(R.id.iwImage)

    }

}